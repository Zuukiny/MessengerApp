package streams;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;


public class socketTest1 {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 7777;
    private static final byte singleByte = (byte) 1;

    public static void main(String[] args) {
        try {
            // Create socket
            Socket socket = new Socket(LOCALHOST, PORT);
            System.out.println("Connected to server");

            // write
            OutputStream out = socket.getOutputStream();
            out.write(singleByte);

            // read
            InputStream in = socket.getInputStream();
            System.out.println("Received from server: " + in.read());

            // Close socket
            socket.close();
            System.out.println("Disconnected from server");


        } catch (UnknownHostException e) {
            System.out.println("Server not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error " + e.getMessage());
        }
    }
}
