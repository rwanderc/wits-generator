package com.wandercosta.witsgenerator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wandercosta.witsgenerator.connection.TcpServer;
import com.wandercosta.witsgenerator.generator.WitsGenerator;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Class to test WitsServer.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class WitsServerTest {

    private static final String NULL_TCPSERVER = "TcpServer must be provided.";
    private static final String NULL_WITSGENERATOR = "WitsGenerator must be provided.";
    private static final String WRONG_PORT = "Port must be between 1 and 65535, inclusive.";
    private static final String WRONG_FREQUENCY
            = "Frequency must be provided and be greater than 0.";
    private static final String WRONG_RECORDS = "Records must be between 1 and 99, inclusive.";
    private static final String WRONG_ITEMS = "Items must be between 1 and 99, inclusive.";

    private static final int DUMMY_PORT = 10_100;
    private static final int DUMMY_FREQUENCY = 1_000;
    private static final int DUMMY_RECORDS = 5;
    private static final int DUMMY_ITEMS = 10;

    @Mock
    private TcpServer tcpServer;

    @Mock
    private WitsGenerator generator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithNullTcpServer() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(NULL_TCPSERVER);

        new WitsServer(null, null, 0, 0, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithNullWitsGenerator() {
        exception.expect(NullPointerException.class);
        exception.expectMessage(NULL_WITSGENERATOR);

        new WitsServer(tcpServer, null, 0, 0, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongPortNegative() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_PORT);

        new WitsServer(tcpServer, generator, -1, 0, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongPortZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_PORT);

        new WitsServer(tcpServer, generator, 0, 0, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongFrequencyNegative() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_FREQUENCY);

        new WitsServer(tcpServer, generator, DUMMY_PORT, -1, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongFrequencyZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_FREQUENCY);

        new WitsServer(tcpServer, generator, DUMMY_PORT, 0, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongRecordsNegative() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_RECORDS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, -1, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongRecordsZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_RECORDS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, 0, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongRecordsHundred() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_RECORDS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, 100, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongÎtemsNegative() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_ITEMS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, DUMMY_RECORDS, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongItemsZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_ITEMS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, DUMMY_RECORDS, 0);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToStartWithWrongItemsHundred() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(WRONG_ITEMS);

        new WitsServer(tcpServer, generator, DUMMY_PORT, DUMMY_FREQUENCY, DUMMY_RECORDS, 0);
    }

    @Test
    public void shouldStartAndStop() throws IOException, InterruptedException {
        String mockedData = "mockedData";
        when(generator.generate(DUMMY_RECORDS, DUMMY_ITEMS)).thenReturn(mockedData, mockedData);

        WitsServer witsServer = new WitsServer(
                tcpServer,
                generator,
                DUMMY_PORT,
                DUMMY_FREQUENCY,
                DUMMY_RECORDS,
                DUMMY_ITEMS);
        Thread witsServerThread = new Thread(() -> runWitsServer(witsServer));
        witsServerThread.start();

        // Sleep enough for the WitsServer to execute twice iteration
        Thread.sleep((long) (DUMMY_FREQUENCY * 1.9));

        witsServer.stop();
        witsServerThread.interrupt();

        verify(tcpServer).start();
        verify(tcpServer, times(2)).writeln(mockedData);
        verify(generator, times(2)).generate(DUMMY_RECORDS, DUMMY_ITEMS);
    }

    private void runWitsServer(WitsServer witsServer) {
        try {
            witsServer.start();
        } catch (IOException ex) {
        }
    }

}
