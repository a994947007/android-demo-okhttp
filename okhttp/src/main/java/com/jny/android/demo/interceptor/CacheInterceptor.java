package com.jny.android.demo.interceptor;

import com.jny.android.demo.okhttp.Interceptor;
import com.jny.android.demo.okhttp.RealInterceptorChain;
import com.jny.android.demo.okhttp.Response;

import java.io.IOException;

public class CacheInterceptor implements Interceptor {

    Response response = null;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (response != null) {
            return response;
        }
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        response = chain.proceed(realInterceptorChain.getRequest());
        return response;
    }
}
