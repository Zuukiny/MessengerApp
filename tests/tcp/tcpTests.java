package tcp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class tcpTests {

    @Test
    public void testConnectionFactory() throws UnknownHostException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory(8080);

        System.out.println("Test: opening connection");
        connectionFactory.acceptNewConnections();
    }

    @Test
    public void testClientConnection()
            throws UnknownHostException, IOException {

        Socket clientSocket = new Socket("localhost", 8080);

        // read
        InputStream clientSocketIn = clientSocket.getInputStream();
        int message = clientSocketIn.read();
        System.out.println("received from Server: " + message);

        // write
        OutputStream clientSocketOut = clientSocket.getOutputStream();
        clientSocketOut.write(42);
        System.out.println("Sent to server: 42");

    }
}
