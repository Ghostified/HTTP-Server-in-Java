package org.codefromscratch.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();

    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;

        //add a HttpParsingException try catch block for runtime errors
        try {
            request = httpParser.parseHttpRequest(
                    generateValidGETTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }

        assertEquals(request.getMethod(), HttpMethod.GET);
    }


    @Test
    void parseHttpRequestBadMethod1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName1()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_INTERNAL_NOT_IMPLEMENTED);
        }


    }

    @Test
    void parseHttpRequestBadMethod2 () {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName2()
            );

            fail();
        } catch (HttpParsingException e){
           assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_INTERNAL_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestInvalidNumberOfItems1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseRequestInvalidNumberOfItems()
            );

            fail();
        } catch (HttpParsingException e){
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    //method to generate a test case for a get request and Request line and version
    private InputStream generateValidGETTestCase(){
        String rawData = //" 23:44:17.781 [Thread-0] INFO org.codefromscratch.httpserver.core.ServerListenerThread --  *Connection Accepted/0:0:0:0:0:0:0:1\r\n" +
                "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Brave\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Linux\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8\r\n" +
                "Sec-GPC: 1\r\n" +
                "Accept-Language: en-US,en\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Test CASE FOR A BAD REQUEST LINE AND A BAD METHOD NAME


    private InputStream generateBadTestCaseMethodName1(){
        String rawData = //" 23:44:17.781 [Thread-0] INFO org.codefromscratch.httpserver.core.ServerListenerThread --  *Connection Accepted/0:0:0:0:0:0:0:1\r\n" +
                "GeT / HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Test Case  to check for method name length
    private InputStream generateBadTestCaseMethodName2(){
        String rawData =
                "GETTTTTT/ HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Test of more than three items in the request line
    private InputStream generateBadTestCaseRequestInvalidNumberOfItems(){
        String rawData =
                "GET/ AAAAAA HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

}