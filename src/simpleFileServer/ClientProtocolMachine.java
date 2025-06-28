package simpleFileServer;

import java.io.*;
import java.net.Socket;

public class ClientProtocolMachine {
    Socket clientSocket;
    InputStream inputStream;
    OutputStream outputStream;

    public ClientProtocolMachine(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void getFile(String fileName) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        DataInputStream dis = new DataInputStream(inputStream);

        PDU.writeGETPDU(dos, fileName);


        byte version = dis.readByte();
        byte command = dis.readByte();

        if (command == PDU.OK && version == 1) {
            // Read next few info bytes of PDU
            String fileNameReceived = dis.readUTF();
            long messageLength = dis.readLong();

            // Prepare to write into file
            File fileToWriteTo = new File("Copy_" + fileNameReceived);
            DataOutputStream dosIntoFile = new DataOutputStream(new FileOutputStream(fileToWriteTo));
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalRead = 0;

            while (totalRead < messageLength) {
                bytesRead = dis.read(buffer);
                if (bytesRead == -1) break;
                dosIntoFile.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
        }

        if (command == PDU.ERROR) {
            throw new FileNotFoundException("The file to search for hasn't been found!");
        }
    }

    public void putFile(String fileName) throws FileNotFoundException, IOException {
        File fileToSend = new File(fileName);

        DataOutputStream dos = new DataOutputStream(outputStream);
        DataInputStream dis = new DataInputStream(new FileInputStream(fileToSend));

        byte[] buffer = new byte[1024];
        int messageBytes;

        ///  SEND DATA
        PDU.writePUTPDU(dos, fileName); // Write PDU package metadata to the stream beforehand
        dos.writeLong(fileToSend.length());
        while ((messageBytes = dis.read(buffer)) != -1) {
            dos.write(buffer, 0, messageBytes);
        }
    }
}
