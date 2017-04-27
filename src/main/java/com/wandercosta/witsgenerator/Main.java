package com.wandercosta.witsgenerator;

import com.wandercosta.witsgenerator.connection.TcpServer;
import com.wandercosta.witsgenerator.generator.WitsGenerator;
import com.wandercosta.witsgenerator.generator.WitsLineGenerator;
import java.io.IOException;
import java.util.Arrays;
import javax.net.ServerSocketFactory;

/**
 * The main class to run the application.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class Main {

    public static void main(String... args) throws IOException {
        boolean error = false;

        if (args == null || args.length != 4) {
            error = true;
            System.err.println("Wrong input!");
        }

        if (!error) {
            try {
                int port = Integer.valueOf(args[0]);
                int frequency = Integer.valueOf(args[1]);
                int records = Integer.valueOf(args[2]);
                int items = Integer.valueOf(args[3]);

                ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
                TcpServer server = new TcpServer(serverSocketFactory, port);
                WitsGenerator generator = new WitsGenerator(new WitsLineGenerator());
                WitsServer wits = new WitsServer(server, generator, port, frequency, records, items);
                wits.start();
            } catch (NumberFormatException ex) {
                error = true;
                System.err.println("Wrong input!");
            }
        }

        if (error || Arrays.asList(args).contains("--help")) {
            System.out.println(help());
            System.exit(1);
        }
    }

    public static String help() {
        return "Usage: java -jar WitsGenerator.jar [port] [frequency] [records] [items]\n"
                + "Creates a socket server and transmit Wits randomic data from time\n"
                + "to time. Only allows ONE single client connected.\n"
                + "\n"
                + "Mandatory arguments\n"
                + "  port\t\tThe port to run the socket Server\n"
                + "  frequency\tThe frequency, in seconds, of the data generation\n"
                + "  records\tThe amount of records to be transmitted\n"
                + "  items\t\tThe amount of items to be transmitted in each reacord";
    }

}
