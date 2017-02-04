package com.testapptwo;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.request.TextRequestInfo;
import com.testapptwo.api.request.ImageRequestInfo;
import com.testapptwo.api.request.LoginRequestInfo;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiTest {

    private static final String TEST_TOKEN = "XU3Ki49X0v5Z4BRddQuNZ7L8eT0aaWALzECK2DsCHtznewv6QUJNI8qpEGiriECm";

    @Test
    public void testSignUp() {
        System.out.println("testSignUp");
        ApiWrapper.getApi().signUp(new LoginRequestInfo("qwerty123", "qwertty123213"))
                .subscribe(userInfo -> {
                    System.out.println(userInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testSignIn() {
        System.out.println("testSignIn");
        ApiWrapper.getApi().signIn(new LoginRequestInfo("qwerty12", "qwertty123213"))
                .subscribe(userInfo -> {
                    System.out.println(userInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testGetImage() {
        System.out.println("testGetImage");
        ApiWrapper.getApi().getImage(1, TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testPostImage() {
        System.out.println("testPostImage");
        ImageRequestInfo imageRequestInfo = new ImageRequestInfo("base64", 0, 0, 0);
        ApiWrapper.getApi().postImage(imageRequestInfo, TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testDeleteImage() {
        System.out.println("testDeleteImage");
        ApiWrapper.getApi().deleteImage(1, TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testGetComment() {
        System.out.println("testGetComment");
        ApiWrapper.getApi().getComment(11212, 1, TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testPostComment() {
        System.out.println("testPostComment");
        ApiWrapper.getApi().postComment(11212, new TextRequestInfo("awersome"), TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }

    @Test
    public void testDeleteComment() {
        System.out.println("testDeleteComment");
        ApiWrapper.getApi().deleteComment(11212, 12, TEST_TOKEN)
                .subscribe(responseInfo -> {
                    System.out.println(responseInfo);
                }, throwable -> {
                    System.out.println("error " + throwable.getMessage());
                });
    }
}