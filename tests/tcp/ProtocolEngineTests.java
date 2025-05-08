package tcp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.EOFException;
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

///////////////////////////////////////////////////////////////////////////////////////
//                               IncrementServer                                     //
///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void runIncrementServer() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory(new IncrementServer());
        System.out.println("test: going to accept new connections");
        connectionFactory.acceptNewConnections();
    }

    @Test
    public void testIncrementServer() throws IOException {
        // 1. connect
        Socket newConnection = new Socket("localhost", 7777);
        byte HowManyIncrements = 20;
        byte originalMessage = 42;
        byte sendMessage;
        byte readMessage = 0;

        // Differentiation between sendMessage and originalMessage is just for Assertion Test and clear separation of values;
        sendMessage = originalMessage;


        // First sent byte defines, how often this message should be incremented
        // Maximum increments is capped at 10 per connection.
        newConnection.getOutputStream().write(HowManyIncrements);


        for (int repetition = 0; repetition < HowManyIncrements && repetition < 10; repetition++) {
            // 2. write message
            newConnection.getOutputStream().write(sendMessage);
            System.out.println("sent: " + sendMessage);

            // 3. read message
            readMessage = (byte) newConnection.getInputStream().read();
            System.out.println("read: " + readMessage);

            // Set sendMessage to readMessage
            sendMessage = readMessage;

            // Visually seperate iterations from each other
            if (repetition < 9) {
                System.out.println("<<<<< Next iteration: " + (repetition + 1) + " >>>>> ");
            }
        }

        // 4. test if incremented correctly
        Assertions.assertEquals(originalMessage + 10, readMessage);
    }

///////////////////////////////////////////////////////////////////////////////////////
//                              DataStreamServer                                     //
///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void runDataStreamServer() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory(new DatastreamServer());
        System.out.println("test: going to accept new connections");
        connectionFactory.acceptNewConnections();
    }

    @Test
    public void testDataStreamServerValid () throws IOException {
        Socket newConnection = new Socket("localhost", 7777);

        // send message
        long longSendValue = 190000000L; // 190.000.000
        int intSendValue = 420000; // 420.000
        String stringSendValue = "Hello World";

        // receive message
        long longReadValue;
        int intReadValue;
        String stringReadValue;

        // Bind DataIn- & DataOutputStream to the corresponding Stream of the Socket
        DataOutputStream dos = new DataOutputStream(newConnection.getOutputStream());
        DataInputStream dis = new DataInputStream(newConnection.getInputStream());

        // Send bytes in the following order
        //      - Long
        //      - Int
        //      - String

        dos.writeLong(longSendValue);
        dos.writeInt(intSendValue);
        dos.writeUTF(stringSendValue);

        System.out.println("sent long: " + longSendValue);
        System.out.println("sent int: " + intSendValue);
        System.out.println("sent String: " + stringSendValue);

        // Read bytes in the following order
        //      - String
        //      - Int
        //      - Long

        stringReadValue = dis.readUTF();
        intReadValue = dis.readInt();
        longReadValue = dis.readLong();

        System.out.println("read String: " + stringReadValue);
        System.out.println("read int: " + intReadValue);
        System.out.println("read Long: " + longReadValue);


        // Assertion tests
        Assertions.assertEquals(longSendValue, longReadValue);
        Assertions.assertEquals(intSendValue, intReadValue);
        Assertions.assertEquals(stringSendValue, stringReadValue);
    }

    @Test
    public void testDataStreamServerInvalid() throws IOException, EOFException {
        Socket newConnection = new Socket("localhost", 7777);

        // send message
        long longSendValue = 190000000L;
        int intSendValue = 420000;
        String stringSendValue = "Hello World";

        // receive message
        long longReadValue = 0;
        int intReadValue = 0;
        String stringReadValue = "";

        // Bind DataIn- & DataOutputStream to the corresponding Stream of the Socket
        DataOutputStream dos = new DataOutputStream(newConnection.getOutputStream());
        DataInputStream dis = new DataInputStream(newConnection.getInputStream());

        // Send bytes in the following order
        //      - Long
        //      - Int
        //      - String

        dos.writeLong(longSendValue);
        dos.writeInt(intSendValue);
        dos.writeUTF(stringSendValue);

        System.out.println("sent long: " + longSendValue);
        System.out.println("sent int: " + intSendValue);
        System.out.println("sent String: " + stringSendValue);

        ///  ORDER ALTERNATED, DATA IS ALLOCATED INCORRECTLY ///
        // Read bytes in the following order
        //      - Int
        //      - String
        //      - Long

        intReadValue = dis.readInt();
        longReadValue = dis.readLong();
        dos.flush();

        System.out.println("read Int: " + intReadValue);
        System.out.println("read String: " + stringReadValue);
        System.out.println("read Long: " + longReadValue);


        // Assertion tests
        Assertions.assertNotEquals(intSendValue, intReadValue);
        Assertions.assertNotEquals(longSendValue, longReadValue);
        Assertions.assertNotEquals(stringSendValue, stringReadValue);
    }
}