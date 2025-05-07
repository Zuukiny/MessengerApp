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
        ServerSocket srv = new ServerSocket(7777);
        Socket newConnection = srv.accept();
        // got new connection

        // read a byte
        InputStream is = newConnection.getInputStream();
        int readValue = is.read();
        System.out.println("serverSocket side: " + readValue);

        // write
        OutputStream os = newConnection.getOutputStream();
        os.write(readValue++);
    }

    @Test
    void openSocketWriteAndRead() throws IOException, InterruptedException {
        Socket newConnection = new Socket("localhost", 7777);

        // write
        OutputStream os = newConnection.getOutputStream();
        int value = 42;
        Thread.sleep(1000*5);
        os.write(value);
        System.out.println("sent: " + value);

        // read a byte
        InputStream is = newConnection.getInputStream();
        int readValue = is.read();
        System.out.println("read: " + readValue);
    }
}
