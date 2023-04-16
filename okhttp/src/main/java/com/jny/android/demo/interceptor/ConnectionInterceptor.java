package com.jny.android.demo.interceptor;

import com.jny.android.demo.okhttp.HttpUtils;
import com.jny.android.demo.okhttp.Interceptor;
import com.jny.android.demo.okhttp.RealInterceptorChain;
import com.jny.android.demo.okhttp.Request;
import com.jny.android.demo.okhttp.Response;

import java.io.IOException;

public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Request request = realInterceptorChain.getRequest();
        return HttpUtils.request(request, 15000, null);
    }
}
