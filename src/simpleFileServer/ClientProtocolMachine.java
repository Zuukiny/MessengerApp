package simpleFileServer;

import java.io.*;
import java.net.Socket;

public class ClientProtocolMachine {
    Socket clientSocket;

    public void getFile(String fileName) throws IOException {
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

        PDU.writeGETPDU(dos, fileName);


        byte version = dis.readByte();
        byte command = dis.readByte();

        if (command == PDU.OK) {
            // Read next few info bytes of PDU
            String fileNameReceived = dis.readUTF();
            long messageLength = dis.readLong();

            // Prepare to write into file
            File fileToWriteTo = new File(fileName + "_Copy");
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
    }

    public void putFile(String fileName) throws FileNotFoundException, IOException {
        File fileToSend = new File(fileName);

        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
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
