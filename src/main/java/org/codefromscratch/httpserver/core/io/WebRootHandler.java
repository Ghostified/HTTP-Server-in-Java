package org.codefromscratch.httpserver.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

public class WebRootHandler {

    private  File webRoot;


    public  WebRootHandler(String webRootPath) throws WebRootNotFoundException{
        webRoot = new File(webRootPath);
        if(!webRoot.exists() || !webRoot.isDirectory()){
            throw new WebRootNotFoundException("Webroot does not exist or is not in the directory");
        }
    }

    private boolean checkIfEndsWithSlash (String relativePath){
        return relativePath.endsWith("/");
    }

    /*
    This method checkes if the webroot exists in the provided webroot folder
    * @param relative path
    @return true idf the path exists inside webroot , false if not
     */
    private boolean checkIfProvidedRelativePathExists(String relativePath){
        File file = new File( webRoot, relativePath);

        if (!file.exists())
            return false;

        try {
            if (file.getCanonicalPath().startsWith(webRoot.getCanonicalPath())) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public  String getFileMimeType (String relativePath) throws FileNotFoundException {
        if(checkIfEndsWithSlash(relativePath)){
            relativePath += "index.html" ; //by default serve index.html, if it exists
        }

        if (!checkIfProvidedRelativePathExists(relativePath)){
            throw  new FileNotFoundException("index.html file not found" + relativePath);
        }

        File file = new File(webRoot, relativePath);

        String mimeType =  URLConnection.getFileNameMap().getContentTypeFor(file.getName());

        if (mimeType == null) {
            return "application/octet-stream";
        }

        return mimeType;
    }

}
