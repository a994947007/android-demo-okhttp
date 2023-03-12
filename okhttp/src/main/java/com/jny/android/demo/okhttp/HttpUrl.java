package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

public class HttpUrl {

    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private static final int DEFAULT_PORT = 80;

    private String scheme;
    private final int port;
    private final String host;
    private final String url;

    public HttpUrl(String url, String scheme, int port, String host) {
        this.url = url;
        this.port = port;
        this.host = host;
    }
    
    public static HttpUrl parse(String url) {
        int pos = 0;
        String scheme = "";
        int port = DEFAULT_PORT;
        String host;
        if (url.regionMatches(true, pos, "https:", 0 ,6)) {
            scheme = SCHEME_HTTPS;
            pos += "https:".length();
        } else if (url.regionMatches(true, pos, "http", 0, 5)) {
            scheme = SCHEME_HTTP;
            pos += "http:".length();
        } else {
            throw new IllegalArgumentException("url is illegal: " + url);
        }
        if (url.regionMatches(true, pos, "//", 0, 2)) {
            pos += "//".length();
        } else {
            throw new IllegalArgumentException("url is illegal:" + url);
        }
        int index = -1;
        index = url.indexOf("/", pos);
        int portSegIndex = -1;
        portSegIndex = url.indexOf(":", pos);
        if (portSegIndex < index) {
            port = Integer.parseInt(url.substring(portSegIndex, index));
            host = url.substring(portSegIndex, index);
        } else {
            host = url.substring(pos, index);
        }
        pos += index;
        return new HttpUrl(url, scheme, port, host);
    }
}