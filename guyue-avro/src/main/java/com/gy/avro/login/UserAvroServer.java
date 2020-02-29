package com.gy.avro.login;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;


/**
 * Reference-URL: http://34.194.184.172/emotix/index.php/2017/05/05/apache-avro-rpc-framework-using-netty-server/
 */
public class UserAvroServer {

    public final static int port = 6666;

    public static class UserRpcImplHandler implements UserRpc {

        @Override
        public UserResponse send(UserRequest request) {
            System.out.println("received RPC: request-name = " + request.getName());
            UserResponse r = new UserResponse();
            r.setName(request.getName() + "-Responsed");
            r.setLoginToken(request.getPassword() + "-Token");
            return r;
        }
    }

    public static void main(String[] args) {

        Server server = new NettyServer(
                new SpecificResponder(UserRpc.class, new UserRpcImplHandler()),
                new InetSocketAddress(port));
        System.out.println("Starting server");
        // usually this would be another app, but for simplicity
        System.out.println("Server started on " + port + " port.");
    }
}
