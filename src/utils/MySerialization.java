package utils;

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

        ///  Using Little Endian
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
    }

    public int[] deserialize(InputStream is) {
        ;
        return new int[0];
    }
}
