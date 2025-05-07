package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class ConnectionFactory {
    private final int DEFAULT_PORT = 7777;
    private final int PORT;


    ConnectionFactory() {
        this.PORT = DEFAULT_PORT;
    }

    ConnectionFactory(int port) {
        this.PORT = port;
    }


    /* Opens a ServerSocket for other sockets to connect to */
    public void acceptNewConnections()
            throws IOException, UnknownHostException {

            // Create Server socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket newConnection = serverSocket.accept();

            System.out.println("Connection established");

            handleConnection(newConnection);

    }


    /* Send and receive ByteStreams */
    private void handleConnection(Socket connectionSocket )
            throws IOException {

        // write
        OutputStream connectionSocketOut = connectionSocket.getOutputStream();
        connectionSocketOut.write(1);
        System.out.println("Sent to client: 1");

        // read
        InputStream connectionSocketIn = connectionSocket.getInputStream();
        int message = connectionSocketIn.read();
        System.out.println("Received from client: " + message);

    }
}
