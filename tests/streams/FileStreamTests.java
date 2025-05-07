package streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamTests {

    @Test
    public void writeAndRead() throws Exception {
        String filename = "testFile.txt";
        OutputStream outputStream = new FileOutputStream(filename);

        //write
        String helloString = "Hello";
        outputStream.write(helloString.getBytes());

        //read
        InputStream inputStream = new FileInputStream(filename);
        byte[] readBuffer = new byte[100];
        inputStream.read(readBuffer);

        String readString = new String(readBuffer);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("wrote: ");
        stringBuilder.append(helloString);
        stringBuilder.append(" | read: ");
        stringBuilder.append(readString);
        System.out.println(stringBuilder.toString());

        readString = readString.substring(0, helloString.length());
        System.out.println(readString);

        Assertions.assertTrue(readString.equals(helloString));
    }
}
