package com.jny.android.demo.okhttp;

import java.io.IOException;

public interface Interceptor {
    Response intercept(Chain chain) throws IOException;

    interface Chain {
        Response proceed(Request request) throws IOException;
    }
}
