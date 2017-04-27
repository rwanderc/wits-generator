package com.wandercosta.witsgenerator.connection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Class to test TcpServer implementation.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class TcpServerTest {

    private static final int DUMMY_PORT = 9090;
    private static final String DUMMY_DATA = "Dummy data";

    @Mock
    private ServerSocketFactory serverSocketFactory;

    @Mock
    private ServerSocket serverSocket;

    @Mock
    private Socket clientSocket;

    @Mock
    private OutputStream outputStream;

    @Test
    public void shouldRunAndWrite() throws IOException {
        when(serverSocketFactory.createServerSocket(DUMMY_PORT)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);
        when(clientSocket.getOutputStream()).thenReturn(outputStream);

        TcpServer server = new TcpServer(serverSocketFactory, DUMMY_PORT);
        server.run();

        verify(serverSocketFactory).createServerSocket(DUMMY_PORT);
        verify(serverSocket).accept();

        when(clientSocket.isClosed()).thenReturn(false);
        server.writeln(DUMMY_DATA);
        verify(outputStream).write(DUMMY_DATA.concat("\n").getBytes());
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToRunWithIOExceptionWrapperInRuntimeException() throws IOException {
        when(serverSocketFactory.createServerSocket(DUMMY_PORT)).thenThrow(new IOException("mocked"));
        new TcpServer(serverSocketFactory, DUMMY_PORT).run();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToInstanciateWithNullFactory() {
        new TcpServer(null, DUMMY_PORT);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToInstanciateWithNullPort() {
        new TcpServer(serverSocketFactory, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToInstanciateWithWrongPort() {
        new TcpServer(serverSocketFactory, 0);
    }

}
