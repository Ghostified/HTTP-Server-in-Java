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

        try {
            // A loop to enable multiple threads ,  several clients can listen to the server 9:20 git
            while (serverSocket.isBound() && !serverSocket.isClosed())  {

                //TCP CONNECTION
                // Open a socket
                Socket socket = serverSocket.accept(); //port open, listening
                LOGGER.info(" *Connection Accepted" + socket.getInetAddress());  //log info of the client
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
            //serverSocket.close();

        } catch (IOException e) {
           // e.printStackTrace();
            LOGGER.error("Problem with setting socket ", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

    }

}
