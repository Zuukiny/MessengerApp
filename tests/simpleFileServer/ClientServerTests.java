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


        while (true) {
            // 🔁 Blockiert, bis ein Client sich verbindet
            Socket clientSocket = serverSocket.accept();

            // 🧵 Starte neuen Thread für diesen Client
            ClientServer handler = new ClientServer(clientSocket);
            new Thread(handler).start();
        }
    }
}
