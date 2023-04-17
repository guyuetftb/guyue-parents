package com.gy.java.net;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;

public class IPAddressTest {
    public static void main(String[] args) {
        try {
            //获得本机的InetAddress信息
            InetAddress IP = InetAddress.getLocalHost();
            showInfo(IP);

            //从名字获得 InetAddress信息
            IP = InetAddress.getByName("www.sina.com.cn");
            showInfo(IP);

            //从IP 地址 获得 InetAddress信息 10.6.22.43
            byte[] bs = new byte[]{(byte) 127, (byte) 0, (byte) 0, (byte) 1};
            IP = InetAddress.getByAddress(bs);
            showInfo(IP);

            Process proc = Runtime.getRuntime().exec("hostname");
            byte[] hostNameBytes = new byte[50];
            InputStream is = proc.getInputStream();
            is.read(hostNameBytes);
            System.out.println(" exec hostname = " + new String(hostNameBytes));
            System.out.println(" length = " + hostNameBytes.length);
            is.close();


            Enumeration<NetworkInterface> enet = NetworkInterface.getNetworkInterfaces();

            while (enet.hasMoreElements()) {
                NetworkInterface net = enet.nextElement();

                if (net.isLoopback())
                    continue;

                Enumeration<InetAddress> eaddr = net.getInetAddresses();

                while (eaddr.hasMoreElements()) {
                    InetAddress inet = eaddr.nextElement();
                    System.out.println("xxxxxxxx = " + inet.getCanonicalHostName());
                }
            }

            Properties sysPro = System.getProperties();
            Enumeration proName = sysPro.propertyNames();
            while (proName.hasMoreElements()) {
                String key = proName.nextElement().toString();
                String value = sysPro.getProperty(key);
                System.out.println(" key = " + key + ", value = " + value);
            }
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将InetAddress 中的信息显示出来
    public static void showInfo(InetAddress IP) {
        String name = IP.getHostName();
        String address = IP.getHostAddress();
        System.out.println(name);
        System.out.println(address);
        System.out.println("------------------------------");
    }
}
