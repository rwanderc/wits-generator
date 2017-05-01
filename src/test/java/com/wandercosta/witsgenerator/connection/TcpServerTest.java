package com.wandercosta.witsgenerator.connection;

import static org.mockito.Mockito.doThrow;
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

    private static final int DUMMY_PORT = 9_090;
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
    public void shouldRunWriteAndStop() throws IOException {
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
        
        server.stopServer();
    }
    
    @Test
    public void shouldFailDuringStopOfServer() throws IOException {
        when(serverSocketFactory.createServerSocket(DUMMY_PORT)).thenReturn(serverSocket);
        doThrow(new IOException()).when(serverSocket).close();
        
        TcpServer tcpServer = new TcpServer(serverSocketFactory, DUMMY_PORT);
        tcpServer.run();
        tcpServer.stopServer();
        
        verify(serverSocket).close();
    }
    @Test
    public void shouldFailDuringStopOfClient() throws IOException {
        when(serverSocketFactory.createServerSocket(DUMMY_PORT)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);
        doThrow(new IOException()).when(clientSocket).close();
        
        TcpServer tcpServer = new TcpServer(serverSocketFactory, DUMMY_PORT);
        tcpServer.run();
        tcpServer.stopServer();
        
        verify(serverSocket).close();
        verify(clientSocket).close();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToRunWithIOExceptionWrappedInRuntimeException() throws IOException {
        when(serverSocketFactory.createServerSocket(DUMMY_PORT))
                .thenThrow(new IOException("mocked"));
        new TcpServer(serverSocketFactory, DUMMY_PORT).run();
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToInstanciateWithNullFactory() {
        new TcpServer(null, DUMMY_PORT);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void shouldFailToInstanciateWithWrongPort() {
        new TcpServer(serverSocketFactory, 0);
    }

}
