package org.codefromscratch.httpserver.core.io;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebRootHandlerTest {

   private WebRootHandler webRootHandler;

    private Method checkIfEndsWithSlashMethod;

    private Method checkIfProvidedRelativePathExistsMethod;



    @BeforeAll
    public  void beforeClass() throws WebRootNotFoundException, NoSuchMethodException {
        webRootHandler = new WebRootHandler("WebRoot");
        Class<WebRootHandler> cls = WebRootHandler.class;
        checkIfEndsWithSlashMethod = cls.getDeclaredMethod("checkIfEndsWithSlash", String.class);
        checkIfEndsWithSlashMethod.setAccessible(true);

        checkIfProvidedRelativePathExistsMethod = cls.getDeclaredMethod("checkIfProvidedRelativePathExists", String.class);
        checkIfProvidedRelativePathExistsMethod.setAccessible(true);

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

    @Test
    void setCheckIfEndsWithSlashMethodFalse() {
        try {
            boolean result = (Boolean) checkIfEndsWithSlashMethod.invoke( webRootHandler, "index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void setCheckIfEndsWithSlashMethodFalse2() {
        try {
            boolean result = (Boolean) checkIfEndsWithSlashMethod.invoke( webRootHandler, "/index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void setCheckIfEndsWithSlashMethodFalse3() {
        try {
            boolean result = (Boolean) checkIfEndsWithSlashMethod.invoke( webRootHandler, "/private/index.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void setCheckIfEndsWithSlashMethodTrue() {
        try {
            boolean result = (Boolean) checkIfEndsWithSlashMethod.invoke( webRootHandler, "/");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void setCheckIfEndsWithSlashMethodTrue2() {
        try {
            boolean result = (Boolean) checkIfEndsWithSlashMethod.invoke( webRootHandler, "/private/");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathExists(){
        try {
            boolean result = (boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler,"/index.html");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testWebRootFilePathExistsGoodRelative(){
        try {
            boolean result = (boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler,"/./././index.html");
            assertTrue(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    //check for a file that does not exist
    @Test
    void testWebRootFilePathExistsDoesNotExist(){
        try {
            boolean result = (boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler,"/./././indexNotHere.html");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    //try to traverse out of the webrrot folder
    @Test
    void testWebRootFilePathExistsInvalid(){
        try {
            boolean result = (boolean) checkIfProvidedRelativePathExistsMethod.invoke(webRootHandler,"/../LICENCE");
            assertFalse(result);
        } catch (IllegalAccessException e) {
            fail(e);
        } catch (InvocationTargetException e) {
            fail(e);
        }
    }

    @Test
    void testGetFileMimeTypeText(){
        try {
            String mimeType = webRootHandler.getFileMimeType("/");
            assertEquals("text/html", mimeType);
        } catch (FileNotFoundException e) {
           fail(e);
        }
    }

    @Test
    void testGetFileMimeTypePng(){
        try {
            String mimeType = webRootHandler.getFileMimeType("/logo.png");
            assertEquals("image/png", mimeType);
        } catch (FileNotFoundException e) {
            fail(e);
        }
    }

    @Test
    void testGetFileMimeTypeDefault(){
        try {
            String mimeType = webRootHandler.getFileMimeType("/favicon.ico");
            assertEquals("application/octet-stream", mimeType);
        } catch (FileNotFoundException e) {
            fail(e);
        }
    }

    @Test
    void testGetFileByteArrayData(){
        try {
           assertTrue(webRootHandler.getFileByteArrayData("/").length> 0);
        } catch (FileNotFoundException e) {
            fail(e);
        } catch (ReadFileException e) {
            fail(e);
        }
    }

    @Test
    void testGetFileByteArrayDataNotThere(){
        try {
           webRootHandler.getFileByteArrayData("/test.html");
        } catch (FileNotFoundException e) {
           //Pass
        } catch (ReadFileException e) {
            fail(e);
        }
    }

}
