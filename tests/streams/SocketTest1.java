package streams;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest1 {

    @Test
    public void TesterPeerTest() throws UnknownHostException, IOException {
        ///  task 1: connect to BNTestPeer.jar
        Socket clientSocket = new Socket("localhost", 7777);

        ///  task 2: send arbitrary Byte
        // Initialize Streams
        InputStream is = clientSocket.getInputStream();
        OutputStream os = clientSocket.getOutputStream();

        byte sendByte = (byte) 42;
        os.write(sendByte);
        System.out.println("Send Byte: " + sendByte);

        ///  task 3: read arbitrary byte
        byte receiveByte = (byte) is.read();
        System.out.println("Receive Byte: " + receiveByte);

        ///  task 4: check if incrementation happened
        boolean wasIncremented = (receiveByte == (sendByte + 1));
        System.out.println("WasIncremented: " + wasIncremented);

        ///  task 5: send byte with value 255. What happens
        sendByte = (byte) 255;
        os.write(sendByte);
        System.out.println("Send Byte: " + sendByte);
    }
}
