package org.codefromscratch.httpserver;

import org.codefromscratch.httpserver.config.Configuration;
import org.codefromscratch.httpserver.config.ConfigurationManager;

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
* Opena nd read files from the file system
* Respond to client : using the http protocol
* All configurations will be stored in a http.json file
 */
public class HttpServer {
    //Driver class with the main method
    public static void main(String[] args) {

        System.out.println("Server Starting....");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        //Check if we are reading the configuration file correctly
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("The HTTP server is using the port: " + conf.getPort());
        System.out.println("The HTTP server  is using  the webroot: " + conf.getWebroot());
    }
}
