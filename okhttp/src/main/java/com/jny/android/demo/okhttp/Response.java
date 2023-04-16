package com.jny.android.demo.okhttp;

public class Response {
    public final ResponseBody responseBody;

    public Response(Builder builder) {
        this.responseBody = builder.responseBody;
    }

    public static class Builder {
        private ResponseBody responseBody;

        public Builder setResponseBody(ResponseBody responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
