package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
Is an interface that sets the contract.
'If your going to handle a connection, you must implement handleConnection(InputStream, OutputStream)'.
*/

public interface ConnectionHandler {
    void handleConnection(InputStream is, OutputStream os)
            throws IOException;
}
