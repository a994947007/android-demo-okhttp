package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;

import okio.BufferedSink;

public abstract class RequestBody {

    public abstract MediaType contentType();

    public long contentLength() throws IOException {
        return -1;
    }

    public abstract void writeTo(BufferedSink sink) throws IOException;

    public static RequestBody create(@Nullable MediaType contentType, String content) {
        Charset charset = Utils.CHARSET_UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Utils.CHARSET_UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
        }
        byte[] bytes = content.getBytes(charset);
        return create(contentType, bytes);
    }


    public static RequestBody create(@Nullable final MediaType contentType, final byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    public static RequestBody create(@Nullable final MediaType contentType, final byte[] content, int offset, int byteCount) {
        if (content == null) throw new NullPointerException("content == null");
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() throws IOException {
                return byteCount;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, byteCount);
            }
        };
    }
}
