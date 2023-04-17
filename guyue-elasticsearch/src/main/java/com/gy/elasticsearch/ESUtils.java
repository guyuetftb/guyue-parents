package com.gy.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by lipeng
 * com.gy.elasticsearch
 * lipeng
 * 2019/1/7
 */
public class ESUtils {

    private static Logger logger = Logger.getLogger(ESUtils.class);

    private static Properties properties     = new Properties();
    private static String     DEFAULT_CONFIG = "es.properties";

    private static final String nodeHostPort;
    private static final String clusterName;
    private static final String type;
    private static final String index;

    /**
     * Transport-Client
     */
    private static TransportClient client = null;

    static {

        InputStream inputStream = null;
        try {
            inputStream = ESUtils.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(" Read es.properties failed. ", e);
            System.exit(1);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(" Close es.properties failed. ", e);
                }
            }
        }

        clusterName = properties.getProperty("guyuecloud.es.hivelog.clustername");
        nodeHostPort = properties.getProperty("guyuecloud.es.hivelog.hostPorts");
        index = properties.getProperty("guyuecloud.es.hivelog.index");
        type = properties.getProperty("guyuecloud.es.hivelog.type");

        if (null == clusterName || clusterName.isEmpty()) {
            logger.error(" The name of cluster is null or empty. ");
            System.exit(1);
        }

        if (null == nodeHostPort || nodeHostPort.isEmpty()) {
            logger.error(" Node host and port is null or empty. ");
            System.exit(1);
        }

        if (null == index || index.isEmpty()) {
            logger.error(" Index is null or empty. ");
            System.exit(1);
        }

        if (null == type || type.isEmpty()) {
            logger.error(" Type is null or empty. ");
            System.exit(1);
        }

        ESParam esParam = new ESParam(clusterName, nodeHostPort, index, type);


        /**
         * 1:通过 setting对象来指定集群配置信息
         */
        Settings setting = Settings.builder()
                .put("cluster.name", esParam.getClusterName()) //指定集群名称
                .put("client.transport.sniff", true) //启动嗅探功能
                .build();

        /**
         * 2：创建客户端
         * 通过setting来创建，若不指定则默认链接的集群名为elasticsearch
         * 链接使用TCP协议即9300
         */
        client = new PreBuiltTransportClient(setting);
        try {
            for (IPPort ip : esParam.getIpSet()) {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(ip.getIp()), ip.getPort()));
            }
        } catch (UnknownHostException e) {
            logger.error(" Create TransportClient failed. ", e);
            System.exit(1);
        }

        /**
         * 3：查看集群信息
         * 注意我的集群结构是：
         *   131的elasticsearch.yml中指定为主节点不能存储数据，
         *   128的elasticsearch.yml中指定不为主节点只能存储数据。
         * 所有控制台只打印了192.168.79.128,只能获取数据节点
         *
         */
        List<DiscoveryNode> connectedNodes = client.connectedNodes();
        for (DiscoveryNode node : connectedNodes) {
            logger.info(" host address " + node.getHostAddress());
        }

        logger.info(" ES TransportClient create Success. ");
    }

    public static TransportClient getESClient() {
        return client;
    }

    private static class IPPort {
        private String ip;
        private int    port;

        public IPPort(String ip,
                      int port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IPPort ipPort = (IPPort) o;
            return port == ipPort.port &&
                    Objects.equals(ip, ipPort.ip);
        }

        @Override
        public int hashCode() {

            return Objects.hash(ip, port);
        }
    }

    private static class ESParam {

        private String      clusterName;
        private String      clusterIpPort;
        private String      index;
        private String      type;
        private Set<IPPort> ipSet;

        ESParam(String clusterName,
                String clusterIpPort,
                String index,
                String type) {
            this.clusterName = clusterName;
            this.clusterIpPort = clusterIpPort;
            this.index = index;
            this.type = type;
            this.ipSet = new HashSet<IPPort>();

            if (null == clusterIpPort || clusterIpPort.isEmpty()) {
                throw new IllegalArgumentException(" Cluster Ip and Port is null or empty. ");
            }

            String[] clusterIpPorts = clusterIpPort.split(";");
            for (String s : clusterIpPorts) {
                String[] ipPort = s.split(":");
                ipSet.add(new IPPort(ipPort[0], Integer.valueOf(ipPort[1])));
            }
        }

        public Set<IPPort> getIpSet() {
            return ipSet;
        }

        public void setIpSet(Set<IPPort> ipSet) {
            this.ipSet = ipSet;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public String getClusterIpPort() {
            return clusterIpPort;
        }

        public void setClusterIpPort(String clusterIpPort) {
            this.clusterIpPort = clusterIpPort;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static void main(String[] args) {
        TransportClient client = getESClient();

        System.out.println(client.getClass());
    }

}

