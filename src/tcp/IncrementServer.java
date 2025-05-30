package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
Simulates communication Protocol:
Receive a byte, increment it, and send it back. (Increment).
*/
public class IncrementServer implements ConnectionHandler{

    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {

        // First byte received specifies how often message should be incremented.
        int amountOfIncrements = is.read();

        // The maximum amount of Increments is 10 per connection. If the received values exceeds 10, set it to 10 manually.
        amountOfIncrements = Math.min(amountOfIncrements, 10);


        // Skip this loop, if amountOfIncrement is 0; End this loop if incrementation has happened ten times.
        for (int repetition = 0; repetition < amountOfIncrements; repetition++) {
            int message = is.read();

            message++;
            os.write(message);
        }

        // Close TCP Connection
        is.close();
        os.close();
    }
}
