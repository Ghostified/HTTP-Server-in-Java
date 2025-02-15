package org.codefromscratch.httpserver.core.io;

import java.io.File;

public class WebRootHandler {

    private  File webRoot;


    public  WebRootHandler(String webRootPath) throws WebRootNotFoundException{
        webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException("Webroot does not exist or is not in the directory");
        }
    }
    public  WebRootHandler(String webRootPath) throws WebRootNotFoundException{
        webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException("Webroot does not exist or is not in the directory");
        }
    }
    public  WebRootHandler(String webRootPath) throws WebRootNotFoundException{
        webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException("Webroot does not exist or is not in the directory");
        }
    }



}
