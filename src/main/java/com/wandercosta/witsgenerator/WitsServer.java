package com.wandercosta.witsgenerator;

import com.wandercosta.witsgenerator.generator.WitsGenerator;
import com.wandercosta.witsgenerator.connection.TcpServer;
import java.io.IOException;

/**
 * This class is responsible for starting the TCP Server thread and writing to its clients from time to time, according
 * to the frequency configured.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsServer {

    private final TcpServer server;
    private final WitsGenerator generator;
    private final int records;
    private final int items;
    private final int frequency;
    private boolean keepRunning;

    public WitsServer(TcpServer server, WitsGenerator generator, int port, int frequency, int records, int items) {
        this.server = server;
        this.records = records;
        this.items = items;
        this.frequency = frequency;
        this.generator = generator;
    }

    public void start() throws IOException {
        keepRunning = true;
        server.start();

        long spentTime;
        while (keepRunning) {
            spentTime = System.currentTimeMillis();
            server.writeln(generator.generate(records, items));
            spentTime = -spentTime + System.currentTimeMillis();

            try {
                Thread.sleep(frequency * 1000 - spentTime);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void stop() {
        keepRunning = false;
    }

}
