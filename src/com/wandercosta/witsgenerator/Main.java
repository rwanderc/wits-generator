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

import java.util.Arrays;

/**
 * This is the main class to run the application.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class Main {

    public static void main(String[] args) {

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

                WitsServer server = new WitsServer(port, frequency, records, items);
                server.start();

            } catch (NumberFormatException ex) {

                error = true;
                System.err.println("Wrong input!");

            } catch (Throwable ex) {

                error = true;
                System.err.println("Unknown error!");

            }

        }

        if (error || Arrays.asList(args).contains("--help")) {
            System.out.println(help());
            System.exit(1);
        }

    }

    public static String help() {

        String message = ""
                + "Usage: java -jar WitsGenerator.jar [port] [frequency] [records] [items]\n"
                + "Creates a socket server and transmit Wits randomic data from time\n"
                + "to time. Only allows ONE single client connected.\n"
                + "\n"
                + "Mandatory arguments\n"
                + "  port\t\tThe port to run the socket Server\n"
                + "  frequency\tThe frequency, in seconds, of the data generation\n"
                + "  records\tThe amount of records to be transmitted\n"
                + "  items\t\tThe amount of items to be transmitted in each reacord";
        return message;

    }

}
