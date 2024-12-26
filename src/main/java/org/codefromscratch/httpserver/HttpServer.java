package org.codefromscratch.httpserver;

import org.codefromscratch.httpserver.config.Configuration;
import org.codefromscratch.httpserver.config.ConfigurationManager;
import org.codefromscratch.httpserver.core.ServerListenerThread;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
* Driver class for the HTTP Server
* The server connects to a network where it gets a request
* Listens for a connection request via port 80
* Encrypted traffic through port 440
* A client e.g browser enters the domain name which initiates the connection request to the server
* The Http Server receives the resources request and processes it
* Server access the root folder for  all the resources and send it  back through the connection
* Server closes the connection
*  TRequirements:
* Read configurations
* Open a socket at a port
* How many connections?
* Read request messages (parsing)
* Open and read files from the file system
* Respond to client : using the http protocol
* All configurations will be stored in a http.json file
 */
public class HttpServer {

    //Using sl4j logger
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class); //used instead of println statements
    //Driver class with the main method
    public static void main(String[] args) {

        LOGGER.info("Server starting");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        //Check if we are reading the configuration file correctly
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("The HTTP server is using the port: " + conf.getPort());
        LOGGER.info("The HTTP server is using the webroot: " + conf.getWebroot());


        //create a listener thread
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Creat
}
