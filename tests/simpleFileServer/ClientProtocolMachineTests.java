package simpleFileServer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class ClientProtocolMachineTests {

    @Test
    public void sendFileToTesterPeer() throws IOException {
        Socket clientSocket = new Socket("localhost", 4444);

        ClientProtocolMachine client = new ClientProtocolMachine(clientSocket.getInputStream(), clientSocket.getOutputStream());

        String fileToSend = "resources/test.txt";
        client.putFile(fileToSend);

        ///  Test: See BNTesterPeer
    }

    @Test
    public void getFileFromTesterPeer() throws IOException {
        Socket clientSocket = new Socket("localhost", 7777);

        ClientProtocolMachine client = new ClientProtocolMachine(clientSocket.getInputStream(), clientSocket.getOutputStream());

        String fileToReceive = "sourceFile.txt";
        client.getFile(fileToReceive);

        ///  Test: See BNTesterPeer
    }
}
