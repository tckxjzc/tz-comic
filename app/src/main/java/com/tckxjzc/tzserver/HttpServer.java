package com.tckxjzc.tzserver;

import android.content.Context;

import com.koushikdutta.async.http.server.AsyncHttpServer;

public class HttpServer {
    Context context;
    AsyncHttpServer server;


    public HttpServer(Context context) {
        this.context = context;

    }

    /**
     * 开启服务
     * @return
     */
    public AsyncHttpServer start() {

        server = new AsyncHttpServer();
        //代理get请求
        server.get(".*", new GetRequestHandle(context));
        //代理post请求
        server.post(".*", new PostRequestHandle(context));
        server.listen(ServerConfig.PORT);
        return server;
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }

    }

}
