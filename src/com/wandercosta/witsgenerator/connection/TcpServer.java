/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Wander Costa (www.wandercosta.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.wandercosta.witsgenerator.connection;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the TCP Socket Server that will open the port for the client to get
 * connected. It only accepts one client connected.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class TcpServer extends Thread {

    private final int port;
    private ServerSocket socket;
    private Socket client;
    private PrintStream clientOut;

    public TcpServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {

            socket = new ServerSocket(port);

            client = socket.accept();
            clientOut = new PrintStream(client.getOutputStream());

        } catch (Exception ex) {
        }

    }

    public void write(String data) {

        if (client != null && clientOut != null && !client.isClosed()) {

            clientOut.print(data);

        }

    }

}
