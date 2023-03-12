package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

import java.nio.charset.Charset;

public class MediaType {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    private final String mediaType;
    private final String type;
    private final String subType;
    @Nullable
    private final String charset;

    public MediaType(String mediaType, String type, String subType, String charset) {
        this.mediaType = mediaType;
        this.type = type;
        this.subType = subType;
        this.charset = charset;
    }

    public static MediaType parse(String mediaType) {
        if (mediaType == null) {
            throw new NullPointerException("mediaType == null");
        }
        String [] args = mediaType.split(";");
        if (args.length == 0) {
            throw new IllegalArgumentException("mediaType is illegal: " + mediaType);
        }
        int typeSubIndex = args[0].indexOf("/");
        if (typeSubIndex <= 0) {
            throw new IllegalArgumentException("mediaType is illegal: " + mediaType);
        }
        String type = args[0].substring(0, typeSubIndex);
        String subType = args[0].substring(typeSubIndex + 1);
        String charset = null;
        if (args.length == 2) {
            int charsetSubIndex = args[1].indexOf("=");
            charset = args[1].substring(charsetSubIndex + 1);
        }
        return new MediaType(mediaType, type, subType, charset);
    }

    public Charset charset() {
        return Charset.forName(charset);
    }

    @Override
    public String toString() {
        return mediaType;
    }
}
