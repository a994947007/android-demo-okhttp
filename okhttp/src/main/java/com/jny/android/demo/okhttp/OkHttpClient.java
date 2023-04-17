package com.jny.android.demo.okhttp;

import java.util.ArrayList;
import java.util.List;

public class OkHttpClient implements Call.Factory{

    /**
     * 直接依赖Factory，可扩展能力更前，这样业务可以实现自己的Factory，甚至可以用一些type字段区分。
     */
    final EventListener.Factory eventListenerFactory;
    final List<Interceptor> interceptors;

    private OkHttpClient(Builder builder) {
        eventListenerFactory = builder.eventListenerFactory;
        interceptors = builder.interceptors;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    @Override
    public Call newCall(Request request) {
        return RealCall.newRealCall(this, request);
    }

    public static class Builder {
        EventListener.Factory eventListenerFactory;
        List<Interceptor> interceptors = new ArrayList<>();

        public Builder() {
            eventListenerFactory = EventListener.factory(EventListener.NONE);
        }

        public Builder eventListener(EventListener eventListener) {
            if (eventListener == null) throw new NullPointerException("eventListener == null");
            eventListenerFactory = EventListener.factory(eventListener);
            return this;
        }

        public Builder eventListenerFactory(EventListener.Factory factory) {
            if (factory == null) throw new NullPointerException("factory == null");
            eventListenerFactory = factory;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) throw new NullPointerException("interceptor == null");
            interceptors.add(interceptor);
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }

}
