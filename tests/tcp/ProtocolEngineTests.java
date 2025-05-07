package tcp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class ProtocolEngineTests {
///////////////////////////////////////////////////////////////////////////////////////
//                                    EchoServer                                     //
///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void runEchoServer() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory(new EchoServer());
        System.out.println("test: going to accept new connections");
        connectionFactory.acceptNewConnections();
    }

    @Test
    public void testEchoServer() throws IOException {
        // test echo server
        // 1. connect
        Socket newConnection = new Socket("localhost", 7777);

        byte byte2Sent = 42;
        // 2. write byte2Sent
        newConnection.getOutputStream().write(byte2Sent);
        System.out.println("sent: " + byte2Sent);

        // read byte2Sent
        int readValue = newConnection.getInputStream().read();
        System.out.println("read: " + readValue);
        // convert to byte
        byte readByte = (byte) readValue;

        // must be the same - it is an echo server.
        Assertions.assertEquals(byte2Sent, readByte);
    }
}