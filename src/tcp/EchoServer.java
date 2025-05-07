package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
It defines the rules for communication over a single socket connection
'When a client connects, here's how we talk to them'
Basically it simulates a protocol (like TCP or UDP) - how data should behave during the communication.
 */

public class EchoServer implements ConnectionHandler{
    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {
        int isMessage = (is.read());

        os.write(isMessage);

        // close (layer 4, namely TCP) connection
        os.close(); // TCP signalisiert dem anderen Prozess, dass nichts mehr kommt.
        is.close(); // dem OS wird gesagt, dass man nichts mehr empfangen will.
    }
}
