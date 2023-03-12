package com.jny.android.demo.okhttp;

import java.io.IOException;

public interface Call {

    void enqueue(Callback callback);

    Response execute() throws IOException;

    interface Factory {
        Call newCall(Request request);
    }
}
