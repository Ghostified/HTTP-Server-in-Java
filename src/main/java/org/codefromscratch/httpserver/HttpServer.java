package org.codefromscratch.httpserver;

import org.codefromscratch.httpserver.config.Configuration;
import org.codefromscratch.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
    //Driver class with the main method
    public static void main(String[] args) {

        System.out.println("Server Starting....");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        //Check if we are reading the configuration file correctly
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("The HTTP server is using the port: " + conf.getPort());
        System.out.println("The HTTP server  is using  the webroot: " + conf.getWebroot());

        //TCP CONNECTION
        // Open a socket
        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort()); //create the socket
            Socket socket = serverSocket.accept(); //port open, listening

            //read something from the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // reading
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>My Web Page</h1><p>This is a web page served by my own java http server </p></body></html>";

            //Http Protocol Client/user agent request
            //CRLF - carriage return line feed
            //Contains a status line
            // ,http version,
            // response code ,
            // response message

            final  String CRLF = "\n\r"; //13 and  10 in ASCII

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status Line - HTTP VERSION,RESPONSE CODE , RESPONSE MESSAGE
                    "Content-length: " + html.getBytes().length + CRLF + //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF ;

            //Writing to the output stream
            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
