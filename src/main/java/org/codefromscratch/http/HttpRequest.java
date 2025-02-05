package org.codefromscratch.http;

import org.codefromscratch.httpserver.config.HttpConfigurationException;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest(){
        
    }

    public HttpMethod getMethod () {
        return  method;
    }

    //getter for the request target


    public String getRequestTarget() {
        return requestTarget;
    }

    void setMethod(String methodName) throws  HttpParsingException{
        for(HttpMethod method: HttpMethod.values()) {
            if  (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_INTERNAL_NOT_IMPLEMENTED
        );
    }

     void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0 ) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }
}
