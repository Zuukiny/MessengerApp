package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DatastreamServer implements ConnectionHandler{
    public void handleConnection(InputStream is, OutputStream os) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        DataInputStream dis = new DataInputStream(is);

        // Retrieve all bytes from the Stream and allocate it to the following order
        //      - Long
        //      - Int
        //      - String

        long readLongValue = dis.readLong();
        int readIntValue = dis.readInt();
        String readStringValue = dis.readUTF();

        // Send all bytes back in reversed order
        //      - String
        //      - Int
        //      - Long

        dos.writeUTF(readStringValue);
        dos.writeInt(readIntValue);
        dos.writeLong(readLongValue);

        // Close connection
        os.close();
        is.close();
    }
}
