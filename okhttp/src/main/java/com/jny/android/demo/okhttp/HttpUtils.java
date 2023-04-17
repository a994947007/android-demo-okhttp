package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

public class HttpUtils {
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";

    @Nullable
    public static Response request(Request request, int timeout, String token) throws IOException{
        HttpURLConnection connection  = null;
        OutputStream os = null;
        InputStream is = null;
        RequestBody requestBody = request.requestBody();
        ResponseBody responseBody = null;
        try {
            URL url = new URL(request.url().toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method());
            connection.setConnectTimeout(timeout);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(request.method());
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            if(token != null && !"".equals(token)){
                connection.addRequestProperty("Authorization", token);
            }
            connection.connect();
            if (null != requestBody) {
                os = connection.getOutputStream();
                Buffer writeBuffer = new Buffer();
                writeBuffer.writeTo(os);
                requestBody.writeTo(writeBuffer);
                writeBuffer.flush();
                if (null != requestBody.contentType()) {
                    connection.setRequestProperty("Content-Type", requestBody.contentType().toString());
                }
            }
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                if (null != is) {
                    BufferedSource buffer = Okio.buffer(Okio.source(is));
                    responseBody = new ResponseBody() {
                        @Override
                        public MediaType contentType() {
                            if (requestBody != null) {
                                return requestBody.contentType();
                            } else {
                                return null;
                            }
                        }

                        @Override
                        public BufferedSource source() {
                            return buffer;
                        }

                    };
                    return new Response.Builder()
                            .setResponseBody(responseBody)
                            .build();
                }
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
