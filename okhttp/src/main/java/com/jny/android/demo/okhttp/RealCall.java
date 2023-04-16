package com.jny.android.demo.okhttp;

import com.jny.android.demo.interceptor.CacheInterceptor;
import com.jny.android.demo.interceptor.ConnectionInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RealCall implements Call{

    private final OkHttpClient client;
    private final Request originalRequest;
    private volatile boolean executed = false;
    private EventListener eventListener;

    public RealCall(OkHttpClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    static RealCall newRealCall(OkHttpClient client, Request originalRequest) {
        RealCall call = new RealCall(client, originalRequest);
        call.eventListener = client.eventListenerFactory.create(call);
        return call;
    }

    @Override
    public void enqueue(Callback callback) {
        
    }

    @Override
    public Response execute() throws IOException {
        if (executed) throw new IllegalStateException("Already Executed");
        executed = true;
        eventListener.callStart(this);
        try {
            Response response = getResponseWithInterceptorChain();
            if (response == null) throw new IOException("Canceled");
            eventListener.callEnd(this);
            return response;
        } catch (IOException e) {
            eventListener.callFailed(this, e);
            throw e;
        }
    }

    /**
     * 这种责任链模式的好处，既能做到递归调用，又可以不相互依赖
     */
    Response getResponseWithInterceptorChain() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>(client.interceptors);
        interceptors.add(new CacheInterceptor());
        interceptors.add(new ConnectionInterceptor());
        Interceptor.Chain chain = new RealInterceptorChain(interceptors, 0, originalRequest);
        return chain.proceed(originalRequest);
    }
}
