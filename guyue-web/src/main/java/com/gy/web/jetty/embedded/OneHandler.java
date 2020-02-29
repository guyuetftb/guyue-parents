package com.gy.web.jetty.embedded;

import org.eclipse.jetty.server.Server;

/**
 * Created by lipeng
 * com.gy.web.jetty.embedded
 * lipeng
 * 2018/10/17
 */
public class OneHandler {
    public static void main(String[] args) throws Exception {
        // 运行程序后, 访问http://localhost:8080，界面返回 Hello World
        Server server = new Server(8080);
        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }
}
