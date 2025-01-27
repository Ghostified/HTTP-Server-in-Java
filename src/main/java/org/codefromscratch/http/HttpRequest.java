package org.codefromscratch.http;

public class HttpRequest extends HttpMessage{

    private String method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest(){
        
    }

    public HttpMethod getMethod () {
        return  method;
    }

     void setMethod(HttpMethod method){
        this.method = method;
    }
}
