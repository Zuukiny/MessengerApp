package simpleFileServer;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ClientProtocolMachineTests {

    @Test
    public void putFileTest() {
        String fileToSend = "resources/UmbreonPokedexEntry.txt";

        ClientProtocolMachine client = new ClientProtocolMachine();
        try {
            client.putFile(fileToSend);
        }
        catch(IOException ioe) {
            System.out.println("Stream behaved weirdly: " + ioe.getMessage());
        }
    }
}
