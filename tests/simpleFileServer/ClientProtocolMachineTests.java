package simpleFileServer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class ClientProtocolMachineTests {

    @Test
    public void sendFileToTesterPeer() throws IOException {
        Socket clientSocket = new Socket("localhost", 7777);

        ClientProtocolMachine client = new ClientProtocolMachine(clientSocket.getInputStream(), clientSocket.getOutputStream());

        String fileToSend = "sourceFile.txt";
        client.putFile(fileToSend);

    }

    @Test
    public void getFileFromTesterPeer() throws IOException {
        Socket clientSocket = new Socket("localhost", 7777);

        ClientProtocolMachine client = new ClientProtocolMachine(clientSocket.getInputStream(), clientSocket.getOutputStream());

        String fileToReceive = "LICENSE";
        client.getFile(fileToReceive);

    }
}
