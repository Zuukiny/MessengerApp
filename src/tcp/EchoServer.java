package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
