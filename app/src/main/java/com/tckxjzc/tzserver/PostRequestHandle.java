package com.tckxjzc.tzserver;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.NameValuePair;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.File;

public class PostRequestHandle implements HttpServerRequestCallback {
    Context context;

    public PostRequestHandle(Context context){
        this.context=context;
    }


    @Override
    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        //copy request
        Uri uri = Uri.parse(ServerConfig.BASE_URL + request.getUrl());

        if(request.getUrl().equals("/favicon.ico")){
            response.code(404);
            response.writeHead();
            response.end();
            return;
        }


        AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(uri, request.getMethod());

        Log.d("TAG-URL", "uri: " + uri);


        //copy request body
        if (request.getBody().length() > 0) {

            asyncHttpRequest.setBody(request.getBody());
        }


        //copy request headers
        Multimap multiMap = request.getHeaders().getMultiMap();
        for (NameValuePair next : multiMap) {
            if (!next.getName().equalsIgnoreCase("host")) {
                asyncHttpRequest.setHeader(next.getName(), next.getValue());
            }
        }
        asyncHttpRequest.setHeader("Referer", ServerConfig.BASE_URL);

        AsyncHttpClient.getDefaultInstance().executeFile(asyncHttpRequest, context.getCacheDir().getAbsolutePath()+"/"+"tmp", new AsyncHttpClient.FileCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
                if (e != null) {
                    response.send(e.toString());
                    return;
                }
                String type = source.headers().get("Content-Type");
                if (type == null) {
                    type = "unknown";
                }
                if (type.contains("text/html")) {
//                    response.send(type, hackHtml(result));
                    return;

                }
                response.setContentType(type);
                Log.d("TAG-URL", "type: " + type);
                response.sendFile(result);

            }
        });
    }
}
