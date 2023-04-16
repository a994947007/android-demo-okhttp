package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.BufferedSource;

public abstract class ResponseBody {
    public abstract MediaType contentType();

    public long contentLength() throws IOException {
        return -1;
    }

    public final InputStream byteStream() {
        return source().inputStream();
    }

    public abstract BufferedSource source();

    public final byte[] bytes() throws IOException {
        long contentLength = contentLength();
        if (contentLength > Integer.MAX_VALUE) {
            throw new IOException("Cannot buffer entire body for content length: " + contentLength);
        }

        BufferedSource source = source();
        byte[] bytes;
        try {
            bytes = source.readByteArray();
        } finally {
            Utils.closeQuietly(source);
        }
        if (contentLength != -1 && contentLength != bytes.length) {
            throw new IOException("Content-Length ("
                    + contentLength
                    + ") and stream length ("
                    + bytes.length
                    + ") disagree");
        }
        return bytes;
    }

    private Charset charset() {
        MediaType contentType = contentType();
        return contentType != null ? contentType.charset() : Utils.CHARSET_UTF_8;
    }

    public final String string() throws IOException {
        BufferedSource source = source();
        try {
            Charset charset = Utils.bomAwareCharset(source, charset());
            return source.readString(charset);
        } finally {
            Utils.closeQuietly(source);
        }
    }
}
