package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MySerialization {

    /* writes all integers of the intArray into the given OutputStream */
    public void serialize(int[] intArray, OutputStream os) throws IOException {
        // int: FF FF FF 03
        List<Byte> byteList = new ArrayList<Byte>();
        int amountOfInts = intArray.length;

        ///  Using Little Endian
        // Provide information about how much integers will be in the InputStream when written to it.
        byteList.add( (byte) amountOfInts);
        byteList.add( (byte) (amountOfInts >> 8) );
        byteList.add( (byte) (amountOfInts >> 16) );
        byteList.add( (byte) (amountOfInts >> 24) );

        // Convert each Integer to bytes, which later will be written to the InputStream.
        for (int integer : intArray) {
            byteList.add( (byte) integer );
            byteList.add( (byte) (integer >> 8) );
            byteList.add( (byte) (integer >> 16) );
            byteList.add( (byte) (integer >> 24) );
        }

        byte[] byteArray = new byte[byteList.size()];
        int byteArrayIndex = 0;

        // Convert and Copy values of ArrayList<Byte> to Array<Byte>
        for (byte byteValue : byteList) {
            byteArray[byteArrayIndex] = byteValue;
            byteArrayIndex++;
        }

        os.write(byteArray);
        os.close();
    }

    public int[] deserialize(InputStream is) throws IOException {

        // Contains information about how long the InputStream will last (easier measurement for later iteration)
        byte b1 = (byte) is.read(); // 4
        byte b2 = (byte) is.read(); // 0
        byte b3 = (byte) is.read(); // 0
        byte b4 = (byte) is.read(); // 0

        ///  Received Bytes are formatted in Little Endian
        // The first read Byte should be the least significant Byte
        int lengthOfStream = (0xFF & b4) << 24 | ((0xFF & b3) << 16) | ((0xFF & b2) << 8) | (0xFF & b1);

        int[] deserializedArray = new int[lengthOfStream];

        for (int i = 0; i < lengthOfStream; i++) {
            b1 = (byte) is.read();
            b2 = (byte) is.read();
            b3 = (byte) is.read();
            b4 = (byte) is.read();

            int mergedInteger = (0xFF & b4) << 24 | (0xFF & b3) << 16 | ((0xFF & b2) << 8) | (0xFF & b1);
            deserializedArray[i] = mergedInteger;
        }
        is.close();

        return deserializedArray;
    }
}
