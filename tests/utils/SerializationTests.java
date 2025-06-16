package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class SerializationTests {

    private String sampleData = "Hallo Welt, hier ist ein Java Programm. Ich wurde von der Zeile aus in eine Datei geschrieben. Das ist schon zeimlich Banonkas :)";;
    private File sourceFile;
    private File targetFile;

    @Test
    public void arrayTest() throws IOException {
        MySerialization ms = new MySerialization();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int[] sample = new int[]{1, 2, 200, 1445, 1234567890};
        ms.serialize(sample, os);
        byte[] serializedData = os.toByteArray();
        InputStream is = new ByteArrayInputStream(serializedData);
        int[] result = ms.deserialize(is);
        Assertions.assertArrayEquals(sample, result);
    }

    @Test
    public void serverFileTest() throws IOException {
        // Create File with some sample Data
        String fileNameSource = "sourceFile.txt";
        sourceFile = new File(fileNameSource);

        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);


        // Create Serversocket & Outputstream
        ServerSocket serverSocket = new ServerSocket(7777);
        Socket clientConnection = serverSocket.accept();

        OutputStream os = clientConnection.getOutputStream();

        // Use serialization method to write file content to the OutputStream
        MySerialization ms = new MySerialization();
        ms.serializeFile(sourceFile, os);

        // Test
        DataInputStream dis = new DataInputStream(new FileInputStream(sourceFile));
        String sourceFileAsString = dis.readUTF();

        Assertions.assertEquals(sampleData, sourceFileAsString);
    }

    @Test
    public void clientFileTest() throws IOException {
        // Create File where content is retrieved by a server
        String fileNameTarget = "targetFile.txt";
        targetFile = new File(fileNameTarget);

        // Create ClientSocket & InputStream
        Socket clientSocket = new Socket("localhost", 7777);
        InputStream is = clientSocket.getInputStream();

        // Use deserialization method to read file content of sourceFile and copy
        MySerialization ms = new MySerialization();
        ms.deserializeFile(targetFile, is);

        // Test
        DataInputStream dis = new DataInputStream(new FileInputStream(targetFile));
        String targetFileAsString = dis.readUTF();

        Assertions.assertEquals(sampleData, targetFileAsString);
    }
}
