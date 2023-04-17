package com.gy.web.jetty.embedded;

import org.eclipse.jetty.server.Server;

/**
 * Created by lipeng
 * com.gy.web.jetty.embedded
 * lipeng
 * 2018/10/17
 */
public class SimplestServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
