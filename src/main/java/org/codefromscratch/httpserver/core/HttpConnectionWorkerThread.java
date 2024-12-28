package org.codefromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//Handles all communication and connection
public class HttpConnectionWorkerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public  void run(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //read something from the socket
        try {
             inputStream = socket.getInputStream();
             outputStream = socket.getOutputStream();

             //read in the bytes comming from the server input stream
//             int _byte ;
//             while ( (_byte = inputStream.read()) >= 0){
//                 System.out.print((char) _byte);
//             }

            // reading
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>My Web Page</h1><p>This is a web page served by my own java http server </p></body></html>";

            //Http Protocol Client/user agent request
            //CRLF - carriage return line feed
            //Contains a status line
            // ,http version,
            // response code ,
            // response message

            final String CRLF = "\n\r"; //13 and  10 in ASCII

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status Line - HTTP VERSION,RESPONSE CODE , RESPONSE MESSAGE
                            "Content-length: " + html.getBytes().length + CRLF + //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            //Writing to the output stream
            outputStream.write(response.getBytes());



            LOGGER.info("Connection Processing Finished");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }

//            inputStream.close();
//            outputStream.close();
//            socket.close();
        }


    }
}
