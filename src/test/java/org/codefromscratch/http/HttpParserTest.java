package org.codefromscratch.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        //assertEquals(request.getRequestTarget(), "NULL");  --- Returns a failed test- idk why
        assertEquals(request.getOriginalHttpVersion(),"HTTP/1.1");
        assertEquals(request.getBestCompatibleHttpVersion(),HttpVersion.HTTP_1_1);
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

    //Test for empty request line -- Empty request before the carriage return
    @Test
    void parseHttpEmptyRequestLine() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseEmptyRequestLine()
            );

            fail();
        } catch (HttpParsingException e){
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    //Test for only carriage return and line feed
    @Test
    void parseHttpEmptyRequestCRnoLF() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseRequestLineCRnoLF()
            );

            fail();
        } catch (HttpParsingException e){
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    //Test for bad HttpVersion
    @Test
    void parseHttpRequestBadHttpVersion() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadHttpVersionTestCase()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }


    }

    //test case for unsupported http version
    @Test
    void parseHttpRequestUnsupportedHttpVersion() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateUnsupportedHttpVersionTestCase()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }


    }

    //test case for supported http version
    @Test
    void parseHttpRequestSupportedHttpVersion1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateSupportedHttpVersion1()
            );
           assertNotNull(request);
           assertEquals(request.getBestCompatibleHttpVersion(),HttpVersion.HTTP_1_1);
           assertEquals(request.getOriginalHttpVersion(),"HTTP/1.2");
        } catch (HttpParsingException e) {
            fail();
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

    //Generator FOR A BAD REQUEST LINE AND A BAD METHOD NAME
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

    //Generator check for method name length
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

    //Generator of more than three items in the request line
    private InputStream generateBadTestCaseRequestInvalidNumberOfItems(){
        String rawData =
                "GET / AAAAAA HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Generator for Empty Request Line
    private InputStream generateBadTestCaseEmptyRequestLine(){
        String rawData =
                "\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Generator for only carriage return and line feed
    private InputStream generateBadTestCaseRequestLineCRnoLF(){
        String rawData =
                "GET / HTTP/1.1\r" + // <------ No line feed
                        "Host: localhost:8080\r\n" +
                        "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //Generator for bad HTTP Version
    private InputStream generateBadHttpVersionTestCase(){
        String rawData =
                "GET / HTP/1.1\r\n" + //Incorrect version of HTTp
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

    //Generator for unsupported Http version
    private InputStream generateUnsupportedHttpVersionTestCase(){
        String rawData =
                "GET / HTTP/2.1\r\n" + //UNSUPPORTED version of HTTp
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

    //Generator for unsupported Http version
    private InputStream generateSupportedHttpVersion1(){
        String rawData =
                "GET / HTTP/1.2\r\n" + //Higher version of HTTp, should fall back to 1.1
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


}