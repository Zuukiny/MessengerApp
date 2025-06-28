package simpleFileServer;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServerTests {

    @Test
    public void testServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);

        Socket clientSocket = serverSocket.accept();

        ClientServer server = new ClientServer(clientSocket);
        server.handle();

    }
}
