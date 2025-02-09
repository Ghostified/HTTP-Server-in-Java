package org.codefromscratch.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpHeadersParserTest {

    private  HttpParser httpParser;
    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException {
        httpParser = new HttpParser();
        Class <HttpParser> cls = HttpParser.class;
        parseHeadersMethod = cls.getDeclaredMethod("parseHeaders", InputStreamReader.class, HttpRequest.class);
        parseHeadersMethod.setAccessible(true);
    }

    @Test
    public void testSimpleHeader() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest();
        parseHeadersMethod.invoke(
                httpParser,
                generateSimpleSingleHeaderMessage(),
                request);
        assertEquals(1, request.getHeaderNames().size());
        assertEquals("localhost:8080", request.getHeader("host"));
    }

    private InputStreamReader generateSimpleSingleHeaderMessage(){
        String rawData =  "Host: localhost:8080\r\n";
//                "GET / HTTP/1.1\r\n" +
//                        "Host: localhost:8080\r\n" +
//                        "Connection: keep-alive\r\n" +
//                        "Cache-Control: max-age=0\r\n" +
//                        "sec-ch-ua: \"Brave\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"\r\n" +
//                        "sec-ch-ua-mobile: ?0\r\n" +
//                        "sec-ch-ua-platform: \"Linux\"\r\n" +
//                        "Upgrade-Insecure-Requests: 1\r\n" +
//                        "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36\r\n" +
//                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8\r\n" +
//                        "Sec-GPC: 1\r\n" +
//                        "Accept-Language: en-US,en\r\n" +
//                        "Sec-Fetch-Site: none\r\n" +
//                        "Sec-Fetch-Mode: navigate\r\n" +
//                        "Sec-Fetch-User: ?1\r\n" +
//                        "Sec-Fetch-Dest: document\r\n" +
//                        "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
//                        "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        return reader;
    }

}
