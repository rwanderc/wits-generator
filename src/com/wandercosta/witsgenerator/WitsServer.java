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
package com.wandercosta.witsgenerator;

import com.wandercosta.witsgenerator.generator.WitsGenerator;
import com.wandercosta.witsgenerator.connection.TcpServer;

/**
 * This class is responsible for starting the TCP Server thread and writing to
 * its clients from time to time, according to the frequency configured.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsServer {

    private final TcpServer server;
    private final WitsGenerator generator;
    private final int frequency;

    public WitsServer(int port, int frequency, int records, int items) {

        this.server = new TcpServer(port);
        this.generator = new WitsGenerator(records, items);
        this.frequency = frequency;

    }

    public void start() {

        server.start();

        long spentTime, wait;

        while (true) {

            spentTime = System.currentTimeMillis();
            server.write(generator.generate());
            spentTime = -spentTime + System.currentTimeMillis();

            wait = frequency * 1000 - spentTime;

            try {
                Thread.sleep(wait);
            } catch (Throwable ex) {
            }

        }

    }

}
