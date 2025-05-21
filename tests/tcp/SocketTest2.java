package tcp;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest2 {

    @Test
    public void TesterPeerTest() throws UnknownHostException, IOException {
        ///  task 1: connect to BNTestPeer.jar
        Socket clientSocket = new Socket("localhost", 7777);

        ///  task 2: send Int value = 42
        // Init Streams
        InputStream is = clientSocket.getInputStream();
        OutputStream os = clientSocket.getOutputStream();

        // Latch DataStreams onto regular streams
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);

        dos.writeInt(42);

        ///  task 3; read UTF-String and print via System.out.print()
        System.out.println("Read String: " + dis.readUTF());

        clientSocket.close();
    }
}
