package simpleFileServer;

import java.io.*;
import java.net.Socket;

public class ClientProtocolMachine {
    Socket clientSocket;

    public void getFile(String fileName) {

    }

    public void putFile(String fileName) throws FileNotFoundException, IOException {
        File fileToSend = new File("/resources/" + fileName + ".txt");

        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream dis = new DataInputStream(new FileInputStream(fileToSend));

        byte[] buffer = new byte[1024];
        int messageBytes;

        PDU.writePUTPDU(dos, fileName); // Write PDU package metadata to the stream beforehand
        dos.writeLong(fileToSend.length());
        while ((messageBytes = dis.read(buffer)) != -1) {
            dos.write(buffer, 0, messageBytes);
        }
    }
}
