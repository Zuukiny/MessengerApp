package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MySerialization {

    /* writes all integers of the intArray into the given OutputStream */
    public void serialize(int[] intArray, OutputStream os) throws IOException {
        // int: FF FF FF 03
        List<Byte> byteList = new ArrayList<Byte>();
        int amountOfInts = intArray.length;

        ///  Using Little Endian
        // Provide information about how many integers will be in the OutputStream when written to it.
        byteList.add( (byte) amountOfInts);
        byteList.add( (byte) (amountOfInts >> 8) );
        byteList.add( (byte) (amountOfInts >> 16) );
        byteList.add( (byte) (amountOfInts >> 24) );

        // Convert each Integer to bytes, which later will be written to the OutputStream.
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

    public void serializeFile(File sourceFile, OutputStream os) throws IOException {
        // File muss deserialized werden und in den os (welcher ein daos ist) geschickt werden.

        long lenghtOfFile = sourceFile.length();
        FileInputStream fis = new FileInputStream(sourceFile);
        List<Byte> byteList = new ArrayList<Byte>();

        ///  Prepare Bytes for sending - Using LITTLE ENDIAN
        // Provide information about how many bytes will be in the FileOutputStream when written to it.
        byteList.add( (byte) lenghtOfFile);
        byteList.add( (byte) (lenghtOfFile >> 8) );
        byteList.add( (byte) (lenghtOfFile >> 16) );
        byteList.add( (byte) (lenghtOfFile >> 24) );
        byteList.add( (byte) (lenghtOfFile >> 32) );
        byteList.add( (byte) (lenghtOfFile >> 40) );
        byteList.add( (byte) (lenghtOfFile >> 48) );
        byteList.add( (byte) (lenghtOfFile >> 56) );


        // Read every Byte from the files initial message and write it to the FileInputStream.
        for (int i = 0; i < lenghtOfFile; i++) {
            byteList.add((byte) fis.read());
        }

        ///  Write to FileInputStream
        for (byte byteValue : byteList) {
            os.write(byteValue);
        }
    }

    public void deserializeFile() {
        ;
    }
}
