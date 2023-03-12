package com.jny.android.demo.okhttp;

import java.io.IOException;

public interface EventListener {

    EventListener NONE = new EventListenerAdapter();

    void callStart(Call call);

    void callEnd(Call call);

    public void callFailed(Call call, IOException e);

    static EventListener.Factory factory(final EventListener eventListener) {
        return call -> eventListener;
    }

    interface Factory {
        EventListener create(Call call);
    }
}
