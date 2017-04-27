package com.wandercosta.witsgenerator.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import javax.net.ServerSocketFactory;

/**
 * This is the TCP Socket Server that will open the port for the client to get connected. It only accepts one client
 * connected.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class TcpServer extends Thread {

    private static final String MISSING_FACTORY = "Missing ServerSocket Factory.";
    private static final String MISSING_PORT = "Missing port.";
    private static final String WRONG_PORT = "Port must be greater than 0.";

    private final ServerSocketFactory serverSocketFactory;
    private final int port;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public TcpServer(ServerSocketFactory serverSocketFactory, Integer port) {
        Objects.requireNonNull(serverSocketFactory, MISSING_FACTORY);
        validatePort(port, MISSING_PORT);

        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = serverSocketFactory.createServerSocket(port);
            clientSocket = serverSocket.accept();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeln(String data) throws IOException {
        data = data.concat("\n");
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.getOutputStream().write(data.getBytes());
        }
    }

    private void validatePort(Integer port, String message) {
        Objects.requireNonNull(port, MISSING_PORT);
        if (port <= 0) {
            throw new IllegalArgumentException(WRONG_PORT);
        }
    }

}
