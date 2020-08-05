package com.tckxjzc.tzserver;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.tckxjzc.http.Proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class GetRequestHandle implements HttpServerRequestCallback {

    Context context;
    Proxy proxy=new Proxy(ServerConfig.BASE_URL);

    public GetRequestHandle(Context context){
        this.context=context;
        init();
    }

    private void init(){
        //hack html
        this.proxy.setHack((response,okHttpResponse)->{
            ResponseBody body = okHttpResponse.body();
            assert body != null;
            MediaType mediaType = body.contentType();
            if(!String.valueOf(mediaType).contains("text/html")){
                return false;
            }
            String html = hackHtml(body.byteStream());
            response.setContentType(String.valueOf(mediaType));
            response.send(html);
            response.end();
            return true;
        });
    }

    @Override
    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

        Uri uri = Uri.parse(ServerConfig.BASE_URL + request.getUrl());
        //拦截无用资源请求
        if(request.getUrl().equals("/favicon.ico")||request.getUrl().startsWith("/statics/baidushareapi")){
            response.code(404);
            response.writeHead();
            response.end();
            return;
        }
        //静态资源
        if(request.getUrl().startsWith("/tz_resource")){
            String path = request.getPath().replace("/tz_resource", "resource");
            Log.d("tz_resource", "onRequest: "+path);
            try {
                InputStream stream = context.getAssets().open(path);
                response.sendStream(stream,stream.available());
            } catch (IOException e) {
                handleErr(response,e);
            }
            return;
        }

        //代理
        try {
            proxy.proxyGetRequest(request,response);
        } catch (IOException e) {
           handleErr(response,e);
        }
    }

    /**
     * 修改页面，过滤广告等
     * @param doc
     * @return
     */
    private String hackHtml(InputStream doc) {

        //解析htmL
        Document document = null;
        try {
            document = Jsoup.parse(doc,"utf-8", ServerConfig.BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //去除referrer
        document.head().append("<meta name=\"referrer\" content=\"no-referrer\" />");
        //获取脚本列表
        Elements scripts = document.getElementsByTag("script");
//        //过滤无用脚本
        filterScript(scripts);
//        //remove logo
        Elements mainlogo = document.getElementsByClass("mainlogo");
        if (mainlogo.size() > 0) {
            mainlogo.get(0).remove();
        }

        //add script
//        document.head().append("<script src=\"http://192.168.153.202:8088/resource/dyf1.user.js\"></script>");
        document.head().append("<script src=\"/tz_resource/dyf1.user.js\"></script>");
        return document.outerHtml();

    }

    /**
     * 过滤广告或无用脚本
     * @param scripts
     */
    private void filterScript(Elements scripts) {

        Iterator<Element> iterator = scripts.iterator();
        while (iterator.hasNext()) {
            Element script = iterator.next();
            String src = script.attr("src");
            if (!src.equals("")) {
                Log.d("script", src);
//                script.remove();
                if (src.contains("cnzz.com") || src.contains("HAd") || src.contains("www.jls666.com")) {
                    script.remove();
                }
            } else {

                String content = script.outerHtml();
                if (content.contains("zhanzhang.baidu.com")) {
                    script.remove();
                }
            }
        }

    }

    /**
     * 处理错误
     * @param response
     * @param e
     */
    public void handleErr(AsyncHttpServerResponse response,Exception e){
        response.code(500);
        response.writeHead();
        response.send(e.toString());
        response.end();
        e.printStackTrace();
    }
}
