package com.jny.android.demo.okhttp;

public class Request {

    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";
    private static final String REQUEST_METHOD_DELETE = "DELETE";
    private static final String REQUEST_METHOD_HEAD = "HEAD";
    private static final String REQUEST_METHOD_PUT = "PUT";
    private static final String REQUEST_METHOD_PATCH = "PATCH";

    private final HttpUrl url;
    private final String method;

    public Request(Builder builder) {
        this.url = builder.httpUrl;
        this.method = builder.method;
    }

    public static class Builder {

        HttpUrl httpUrl;
        String method = REQUEST_METHOD_GET;
        RequestBody requestBody;

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            HttpUrl httpUrl = HttpUrl.parse(url);
            this.httpUrl = httpUrl;
            return this;
        }

        public Builder get() {
            return method(REQUEST_METHOD_GET, null);
        }

        public Builder post(RequestBody requestBody) {
            return method(REQUEST_METHOD_POST, requestBody);
        }

        public Builder delete(RequestBody requestBody) {
            return method(REQUEST_METHOD_DELETE, requestBody);
        }

        public Builder head() {
            return method(REQUEST_METHOD_HEAD, null);
        }

        public Builder put(RequestBody requestBody) {
            return method(REQUEST_METHOD_PUT, requestBody);
        }

        public Builder patch(RequestBody requestBody) {
            return method(REQUEST_METHOD_PATCH, requestBody);
        }

        public Builder method(String method, RequestBody requestBody) {
            this.method = method;
            this.requestBody = requestBody;
            return this;
        }

        public Request build() {
            if (httpUrl == null) throw new IllegalArgumentException("url == null");
            return new Request(this);
        }
    }
}
