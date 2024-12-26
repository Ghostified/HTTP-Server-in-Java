package org.codefromscratch.httpserver.core;


import org.codefromscratch.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//Create multiple threads to listen to multiple connections to our server
//In multiple ports and multiple webroot
public class ServerListenerThread extends  Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private  int port;
    private String webroot;
    private  ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {

        //TCP CONNECTION
        // Open a socket
        try {

            Socket socket = serverSocket.accept(); //port open, listening
            LOGGER.info(" *Connection Accepted" + socket.getInetAddress() );  //log info of the client

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
