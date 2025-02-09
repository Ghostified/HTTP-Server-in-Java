package org.codefromscratch.http;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import org.codefromscratch.http.HttpMessage;

import static org.codefromscratch.http.HttpStatusCode.*;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private  HttpVersion bestCompatibleHttpVersion;
    private HashMap<String, String> headers = new HashMap<>();

    HttpRequest(){

    }

    public HttpMethod getMethod () {
        return  method;
    }

    public String getRequestTarget(){
        return  requestTarget;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public Set<String> getHeaderNames(){
        return headers.keySet();
    }

    public String getHeader(String headerName){
        return headers.get(headerName.toLowerCase());
    }

    void setMethod(String methodName) throws  HttpParsingException{
        for(HttpMethod method: HttpMethod.values()) {
            if  (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                SERVER_ERROR_501_INTERNAL_NOT_IMPLEMENTED
        );
    }

    void setHttpVersion(String originalHttpVersion) throws HttpParsingException, BadHttpVersionException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            throw new HttpParsingException(
                    HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED
            );
        }
    }

    void addHeader(String headerName, String headerField){
        headers.put(headerName.toLowerCase(), headerField);

    }
}
