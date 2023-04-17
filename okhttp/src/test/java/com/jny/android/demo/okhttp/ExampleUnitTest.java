package com.jny.android.demo.okhttp;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        System.out.println(HttpUrl.parse("http://www.baidu.com/fdad").toString());
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .get()
                .url("http://www.baidu.com")
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.responseBody.source().readByteArray().length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}