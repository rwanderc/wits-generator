package com.wandercosta.witsgenerator.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import javax.net.ServerSocketFactory;

/**
 * This is the TCP Socket Server that will open the port for the client to get connected. It only
 * accepts one client connected.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class TcpServer extends Thread {

    private static final String MISSING_FACTORY = "Missing ServerSocket Factory.";
    private static final String WRONG_PORT = "Port must be greater than 0.";

    private final ServerSocketFactory serverSocketFactory;
    private final int port;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public TcpServer(ServerSocketFactory serverSocketFactory, int port) {
        validate(serverSocketFactory, port);
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

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
            }
        }
    }

    public void writeln(String data) throws IOException {
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.getOutputStream().write(data.concat("\n").getBytes());
        }
    }

    private void validate(ServerSocketFactory serverSocketFactory, int port) {
        Objects.requireNonNull(serverSocketFactory, MISSING_FACTORY);
        if (port <= 0) {
            throw new IllegalArgumentException(WRONG_PORT);
        }
    }

}
