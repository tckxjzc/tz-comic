package com.tckxjzc.http;

import android.net.Uri;
import android.util.Log;

import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import org.jsoup.internal.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Proxy {
    public Proxy(String baseUrl) {
        this.baseUrl = baseUrl;
        this.baseUri=Uri.parse(baseUrl);
    }
    Hack hack;
    String baseUrl;
    Uri baseUri;
    OkHttpClient client = new OkHttpClient();

    public void setHack(Hack hack){
        this.hack=hack;
    }


    public void proxyGetRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) throws IOException {
        Request getRequest = copyGetRequest(request);
        Response execute = client.newCall(getRequest).execute();

        //复制响应头
        Map<String, List<String>> stringListMap = execute.headers().toMultimap();
        response.getHeaders().addAllMap(multimap2singleMap(stringListMap));


        //复制状态码
        response.code(execute.code());

        //复制响应体
        ResponseBody body = execute.body();

        //判空
        if(body==null){
           response.end();
           Log.e("ResponseBody","------Null------");
           return;
        }
        Log.d("contentLength", "proxyGetRequest: "+ body.contentLength());
        Log.d("contentType", "proxyGetRequest: "+ String.valueOf(body.contentType()));

        //hack
        if(this.hack!=null){
            if(this.hack.handleResponse(response,execute)){
                return;
            }
        }

        response.setContentType(String.valueOf(body.contentType()));
//        response.send(body.string());
//        InputStream inputStream = body.byteStream();
//        response.sendStream(inputStream, body.contentLength());
        byte[] bytes = body.bytes();
        response.send(String.valueOf(body.contentType()),bytes);
//
        response.end();
//        response.proxy();
    }

    /**
     * 复制/转换请求
     * @param request
     * @return
     */
    public Request copyGetRequest(AsyncHttpServerRequest request) {
        //copy headers
        Headers headers = request.getHeaders();
        //移出host
        headers.remove("host");
        headers.remove("referer");
        headers.remove("Accept-Encoding");
        Map<String, String> stringMap = headers.getMultiMap().toSingleMap();

        okhttp3.Headers copyHeaders = okhttp3.Headers.of(stringMap);
//        Log.d("headers",headers+"---");
//        Log.d("copyHeaders",copyHeaders+"---");

        return new Request
                .Builder()
                .get()
                .headers(copyHeaders)
                .url(baseUrl + request.getUrl())
                .build();
    }

    /**
     *
     * @param multiMap
     * @return
     */
    public HashMap<String, String> multimap2singleMap(Map<String, List<String>> multiMap){
        HashMap<String, String> hashMap = new HashMap<>();
        multiMap.forEach((name,list)->{
            hashMap.put(name, StringUtil.join(list,";"));
        });
//        Log.d("multimap2singleMap", "multimap2singleMap: "+hashMap);
        hashMap.remove("content-encoding");
        hashMap.remove("transfer-encoding");
        hashMap.remove("connection");

        return hashMap;
    }
    public static interface Hack{
        boolean handleResponse(AsyncHttpServerResponse response,Response execute) throws IOException;
    }
}
