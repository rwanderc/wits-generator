---
shorturl:    "http://goo.gl/fBxRii"
layout:      post
title:       "WitsGenerator"
subtitle:    "an application to generate random Wits data"
description: "A Java command-line application to generate random Wits data."
date:        2015-12-19 04:43:00
author:      "Wander Costa"
header-img:  "/img/post-bg-rig.jpg"
thumb-img:   "/img/thumb/thumb-java-witsgenerator.jpg"
tags:
- java
- wits
- witsml
- oil
- O&G
- drilling
---

[github]:https://github.com/rwanderc
[code]:https://github.com/rwanderc/wits-generator
[wits-simplified]:http://www.wandercosta.com/wits-simplified/

Carrying on in the context of Oil & Gas, in this post I will share a very simple Java application to stream random Wits data, provided through a TCP Socket Server to a single client. If you are new to Wits protocol, please visit my previous post, [Wits Simplified][wits-simplified].

For this project, I'm using:

* Java JDK 8u73;
* Maven 3.3.9.

There are also other libraries being used (JUnit, Mockito, JaCoCo, etc), however Maven will automatically download them.

This [code][code] is also available in my <i class="fa fa-github"></i> [GitHub][github] page. Feel free to comment if you have any doubt or suggestion. :smile:

### Licensing
This software is licensed under the MIT license, what basically means that you can use it for any purpose ___without any warranty___. :warning:

# Architecture
This application creates a thread to provide a TCP server, generates Wits data and publishes the generated data into the socket of the connected client. Its design is quite simple, and the overall responsibility and behavior of the classes are as described bellow:

* **WitsServer**: orchestrates the start/stop of the resources and is responsible for writing the data into the socket;
* **TcpServer**: is started as other thread, to provide socket connection for the client to connect to;
* **WitsGenerator**: generates Wits blocks of data, by assembling Wits lines;
* **WitsLineGenerator**: generates each Wits line of data.

The class **WitsServer.java** depends on the the others classes, **TcpServer.java**, **WitsGenerator.java** and **WitsLineGenerator.java** (transitively), respecting all the different responsibilities of these classes.

The class **Main.java** is only used to interface the user's call and the application itself, thus it will not be covered in this document. However, it's interesting to look into the class to better understand how the command-line is being interpreted and instantiating the main classes.

# Compilation
There are no runtime dependencies of the application. However, some test libraries are being used to make sure the expected behaviors are produced, thus Maven will download them during test phase.

To compile the project, run `mvn clean install`. After executed, it will result in a tinny JAR file under the `./target` folder, which is the compiled application.

# Execution
The compiled application can be easily executed from the command-line, as the following help describes:
The usage is as follow:
{% highlight text %}
Usage: java -jar wits-generator-1.1.jar [port] [frequency] [records] [items]
Creates a socket server and transmit Wits randomic data from time
to time. Only allows ONE single client connected.

Mandatory arguments
  port		The port to run the socket Server
  frequency	The frequency, in seconds, of the data generation
  records	The amount of records to be transmitted
  items		The amount of items to be transmitted in each reacord
{% endhighlight %}

When executed, it will start listening for the first client at ``port`` and produce ``records x items`` lines of data in each ``frequency`` milliseconds.

All the configuration parameters may be changed in the command-line call, making it really adaptable to provide as many items and records as necessary in the wanted frequency. Make sure to provide reasonable input parameters, so the client can consume the data in less time than the frequency of data creation. Basically, it means that, if you want to consume the generated data from slow or unstable TCP connection (e.g. through a low bandwidth Internet connection, a VPN connection, or an old 10Mbps Ethernet network adapter), you must adapt the frequency and the amount of data being produced to meet your connection limitations. As Wits runs as plain text over socket connection, and _does not provide historical replay_ by default, the TCP protocol may spend some effort trying to resend lost packages.


# Code
I'm not attaching here the Main.class, which is only an interface between the user and the application. If necessary, visit my <i class="fa fa-github"></i> [GitHub][github] page.

#### WitsServer.class
<script src="https://gist.github.com/rwanderc/375a333b53e5201f879af7a8f69a47f3.js"></script>

#### TcpServer.class
<script src="https://gist.github.com/rwanderc/24309ba01c8292f39ce4100bd7fddc3d.js"></script>

#### WitsGenerator.class
<script src="https://gist.github.com/rwanderc/c2a67e2c1c326f659c2c8bacc6c32b96.js"></script>

#### WitsLineGenerator.class
<script src="https://gist.github.com/rwanderc/bbaa7811ae1071b75cf21b6ccee5917c.js"></script>
