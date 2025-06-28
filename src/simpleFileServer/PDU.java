package simpleFileServer;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PDU {
    public static final byte PROTOCOL_VERSION = 1;

    // Command Codes
    public static final byte GET_PDU = 0x00;
    public static final byte PUT_PDU = 0x01;
    public static final byte ERROR = 0x02;
    public static final byte OK = 0x03;

    // Error Codes
    public static final int INVALID_PROTOCOL_VERSION = 0x64; // 100
    public static final int FILE_NOT_FOUND = 0x65; // 101
    public static final int INVALID_COMMAND = 0x66; // 102

    public static void writeGETPDU(DataOutputStream daos, String fileName) throws IOException {
        daos.writeByte(PROTOCOL_VERSION);
        daos.writeByte(GET_PDU);
        daos.writeUTF(fileName);
    }

    public static void writePUTPDU(DataOutputStream daos, String fileName) throws IOException {
        daos.writeByte(PROTOCOL_VERSION);
        daos.writeByte(PUT_PDU);
        daos.writeUTF(fileName);
    }

    public static void writeERROR(DataOutputStream daos, String fileName, int errorCode, String errorMessage) throws IOException {
        daos.writeByte(PROTOCOL_VERSION);
        daos.writeByte(ERROR);
        daos.writeUTF(fileName);
        daos.writeInt(errorCode);
        daos.writeUTF(errorMessage);
    }

    public static void writeOK(DataOutputStream daos, String fileName) throws IOException {
        daos.writeByte(PROTOCOL_VERSION);
        daos.writeByte(OK);
        daos.writeUTF(fileName);
    }
}
