package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

public class SerializationTests {



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
    public void fileTest1() throws IOException {
        String sampleData = "Hallo Welt, hier ist ein Java Programm. Ich wurde von der Zeile aus in eine Datei geschrieben. Das ist schon zeimlich Banonkas :)";
        String fileNameSource = "sourceFile.txt";
        String fileNameTarget = "targetFile.txt";

        // Create temporary file and fill it with some sampleData
        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);
        daos.close();


        // Create two permanent files
        File sourceFile = new File(fileNameSource);
        File targetFile = new File(fileNameTarget);
        MySerialization ms = new MySerialization();

        Socket clientSocket = new Socket("localhost", 7777);


        // serialize data of one file into DataOutputStream
        OutputStream os = clientSocket.getOutputStream();
        InputStream is = clientSocket.getInputStream();

        ms.serializeFile(sourceFile, os);
        ms.deserialize(is);

    }

}
