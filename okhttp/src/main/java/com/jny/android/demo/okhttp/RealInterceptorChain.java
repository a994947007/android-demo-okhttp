package com.jny.android.demo.okhttp;

import java.io.IOException;
import java.util.List;

public class RealInterceptorChain implements Interceptor.Chain {
    private final List<Interceptor> interceptors;
    private final int index;
    private final Request request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, Request request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        return proceed(request, index);
    }

    private Response proceed(Request request, int index) throws IOException {
        RealInterceptorChain next = new RealInterceptorChain(interceptors, index + 1, request);
        Interceptor interceptor = interceptors.get(index);
        Response response = interceptor.intercept(next);
        return response;
    }
}
