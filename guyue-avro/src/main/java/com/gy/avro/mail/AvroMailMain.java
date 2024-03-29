package com.gy.avro.mail;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;

import com.gy.avro.mail.Mail;
import com.gy.avro.mail.Message;

/**
 * Source URL => : https://github.com/phunt/avro-rpc-quickstart/blob/master/src/main/java/example/Main.java
 * Start a server, attach a client, and send a message.
 */
public class AvroMailMain {
    public static class MailImpl implements Mail {
        // in this simple example just return details of the message
        public Utf8 send(Message message) {
            System.out.println("Sending message");
            return new Utf8("Sending message to " + message.getTo().toString()
                    + " from " + message.getFrom().toString()
                    + " with body " + message.getBody().toString());
        }
    }

    private static Server server;

    private static void startServer() {
        server = new NettyServer(new SpecificResponder(Mail.class, new MailImpl()), new InetSocketAddress(65111));
        // the server implements the Mail protocol (MailImpl)
    }

    public static void main(String[] args) throws IOException {
        args = new String[]{
                "127.0.0.1",
                "localhost",
                "This is a avro Message."
        };

        if (args.length != 3) {
            System.out.println("Usage: <to> <from> <body>");
            System.exit(1);
        }

        System.out.println("Starting server");
        // usually this would be another app, but for simplicity
        startServer();
        System.out.println("Server started");

        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(6666));
        // client code - attach to the server and send a message
        Mail proxy = SpecificRequestor.getClient(Mail.class, client);
        System.out.println("Client built, got proxy");

        // fill in the Message record and send it
        Message message = new Message();
        message.setTo(new Utf8(args[0]));
        message.setFrom(new Utf8(args[1]));
        message.setBody(new Utf8(args[2]));
        System.out.println("Calling proxy.send with message:  " + message.toString());
        System.out.println("Result: " + proxy.send(message));

        // cleanup
        client.close();
        server.close();
    }
}
