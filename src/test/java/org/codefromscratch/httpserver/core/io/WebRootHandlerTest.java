package org.codefromscratch.httpserver.core.io;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebRootHandlerTest {

    @BeforeAll
    public  void beforeClass(){

    }

    //test for the root Path
    @Test
    void constructorGoodPath(){
        try {
            WebRootHandler webRootHandler = new WebRootHandler("/home/ghost/Projects/Java/HTTP_Server_In_Java/WebRoot");
        } catch (WebRootNotFoundException e) {
            fail(e);
        }
    }

    //Test for a given path that does not exist
    @Test
    void constructorBadPath(){
        try {
            WebRootHandler webRootHandler = new WebRootHandler("/home/ghost/Projects/Java/HTTP_Server_In_Java/WebRoot2");
            fail();
        } catch (WebRootNotFoundException e) {

        }
    }

    //test for a good relative path
    @Test
    void constructorRelativeGoodPath(){
        try {
            WebRootHandler webRootHandler = new WebRootHandler("WebRoot");

        } catch (WebRootNotFoundException e) {
            fail(e);
        }
    }

    //test for a bad relative path that does not exist
    @Test
    void constructorRelativeBadPath(){
        try {
            WebRootHandler webRootHandler = new WebRootHandler("WebRoot2");
            fail();
        } catch (WebRootNotFoundException e) {

        }
    }

}
