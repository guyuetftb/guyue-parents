package com.gy.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TestCommon {

    //    public static String nodeHost = "10.2.40.10,10.2.40.14,10.2.40.15";
    public static String nodeHost = "127.0.0.1, 127.0.0.1";
//    public static String nodeHost = "10.6.88.46, 10.6.88.46";

    // public static int nodePort = 9300;
    public static String nodePort = "9300, 9301";

    private static String clusterName = "gy-cluster";

    private static TransportClient client = null;

    static {
        System.out.println("------------------> : start ");
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", false)
                .build();

        String[] nodes = nodeHost.split(",");
        String[] ports = nodePort.split(",");
        TransportAddress[] transportAddresses = new TransportAddress[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            try {
                transportAddresses[i] = new TransportAddress(InetAddress.getByName(nodes[i].trim()), Integer.valueOf(ports[i].trim()));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        client = new PreBuiltTransportClient(settings).addTransportAddresses(transportAddresses);
        System.out.println("------------------> : end ");
    }

    public static TransportClient getClient() {
        return client;
    }

}
