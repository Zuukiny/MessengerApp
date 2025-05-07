package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
Handles the actual Socket Infrastructure.
    - binding to a port
    - accepting a client connection

Subsequently, it passes the socket to the connectionHandler.
'Okay, ConnectionHandler, here's a socket. Do your thing'
 */



public class ConnectionFactory {
    public static final int DEFAULT_PORT = 7777;
    private final int PORT;
    private final ConnectionHandler connectionHandler;

    public ConnectionFactory(ConnectionHandler connectionHandler) {
        this(DEFAULT_PORT, connectionHandler);
    }
    public ConnectionFactory(int port, ConnectionHandler connectionHandler) {
        this.PORT = port;
        this.connectionHandler = connectionHandler;
    }

    void acceptNewConnections() throws IOException {
        ServerSocket server = new ServerSocket(this.PORT);
        Socket connectionToClient = server.accept();
        // protocol engine will take over
        this.connectionHandler.handleConnection(
                connectionToClient.getInputStream(),
                connectionToClient.getOutputStream());

    }
}