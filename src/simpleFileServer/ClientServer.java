package simpleFileServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer {
    private final Socket socket;
    private final DataInputStream dis;
    private final DataOutputStream dos;

    public ClientServer(Socket socket) throws IOException {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    public void handle() throws IOException {
        byte version = dis.readByte();
        byte command = dis.readByte();

        if (version != PDU.PROTOCOL_VERSION) {
            sendError(PDU.INVALID_PROTOCOL_VERSION, "Invalid Protocol Version");
        }

        if (command == PDU.GET_PDU) {
            sendFile();
        }
        else if (command == PDU.PUT_PDU) {
            receiveFile();
        }
        else {
            sendError(PDU.INVALID_COMMAND, "Unknown Command");
        }

        dis.close();
        dos.close();
        socket.close();
    }

    public void receiveFile() throws IOException {
        String fileName = dis.readUTF();
        long length = dis.readLong();

        File file = new File("received_" + fileName);

        FileOutputStream fos = new FileOutputStream(file);

        long readBytes = 0;
        while (readBytes < length) {
            fos.write(dis.readByte());
            readBytes ++;
        }

        System.out.println("File Received: " + fileName);
    }

    public void sendFile() throws IOException {
        String fileName = dis.readUTF();
        File file = new File(fileName);

        if (!file.exists()) {
            sendError(PDU.FILE_NOT_FOUND, "File not found");
        }

        PDU.writeOK(dos, fileName);
        dos.writeLong(file.length());
        FileInputStream fis = new FileInputStream(file);

        byte readByte;
        while ((readByte = (byte) fis.read()) != -1) {
            dos.writeByte(readByte);
        }
    }

    private void sendError(int errorCode, String message) throws IOException {
        String fileName = dis.readUTF();

        PDU.writeERROR(
                dos,
                fileName,
                errorCode,
                message
        );
    }
}
