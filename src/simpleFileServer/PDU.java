package simpleFileServer;

import java.io.DataOutputStream;
import java.io.IOException;

public class PDU {
    public static final byte PROTOCOL_VERSION = 1;

    // Command Codes
    private static final byte GET_PDU = 0x00;
    private static final byte PUT_PDU = 0x01;
    private static final byte ERROR = 0x02;
    private static final byte OK = 0x03;

    public static void writePUTPDU(DataOutputStream daos, String fileName) throws IOException {
        daos.writeByte(PROTOCOL_VERSION);
        daos.writeByte(PUT_PDU);
        daos.writeUTF(fileName);
    }


    public byte getGet_Pdu() {
        return GET_PDU;
    }

    public byte getPut_PDU() {
        return PUT_PDU;
    }

    public byte getError() {
        return ERROR;
    }

    public byte getOK() {
        return OK;
    }
}
