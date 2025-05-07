package tcp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class tcpTests {

    @Test
    void openServerSocketReadAndWrite() throws IOException {
        ServerSocket server = new ServerSocket(7777);
        Socket connectionToClient = server.accept();
        // got new connection

        // read a byte
        InputStream is = connectionToClient.getInputStream();
        int readValue = is.read();
        System.out.println("serverSocket side: " + readValue);

        // write
        OutputStream os = connectionToClient.getOutputStream();
        os.write(++readValue);
    }

    @Test
    void openSocketWriteAndRead() throws IOException, InterruptedException {
        Socket clientConnection = new Socket("localhost", 7777);

        // write
        OutputStream os = clientConnection.getOutputStream();
        int value = 42;
        Thread.sleep(1000*5);
        os.write(value);
        System.out.println("sent: " + value);

        // read a byte
        InputStream is = clientConnection.getInputStream();
        int readValue = is.read();
        System.out.println("read: " + readValue);
    }
}
