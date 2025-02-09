package org.codefromscratch.http;

public enum HttpStatusCode {

//    Client Errors
    CLIENT_ERROR_400_BAD_REQUEST (400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED (401, "Method Not Allowed"),
    CLIENT_ERROR_414_BAD_REQUEST (414, "URI Too Long"),

    //SERVER Errors
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR (500, "Internal Server Error"),
    SERVER_ERROR_501_INTERNAL_NOT_IMPLEMENTED (501, "Method Not Implemented"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "Http Version not supported");


    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE){
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }


}
