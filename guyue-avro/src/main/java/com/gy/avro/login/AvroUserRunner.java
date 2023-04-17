package com.gy.avro.login;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AvroUserRunner extends Thread {

    public static final Logger logger = Logger.getLogger(AvroUserRunner.class);

    private static String path = "/Users/lipeng/practice_data/flume_test_dir";
    private String fileName;
    private String threadName;

    public AvroUserRunner(String threadName, String fileName) {
        super(threadName);
        this.fileName = fileName;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            // writeLogFile();
            System.out.println("----------- threadName=" + threadName + ", fileName=" + fileName + ", I am Over!. ");
            sendRpc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException
     */
    private void sendRpc() throws IOException {
        //
        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(6666));
        UserRpc resProxy = SpecificRequestor.getClient(UserRpc.class, client);
        System.out.println("Client built, got proxy");

        try {
            // 文件路径
            int rowNum = 0;
            int cycIndex = 0;
            while (true) {
                UserRequest user = UserRequest.newBuilder()
                        .setName("User-" + rowNum)
                        .setPassword("Password-" + rowNum)
                        .setDevicetoken((System.currentTimeMillis() % 2 == 0 ? "男" : "女"))
                        .build();

                // fill in the Message record and send it
                System.out.println("Calling proxy.send with message:  " + user.toString());
                System.out.println("Result: " + resProxy.send(user));

                rowNum++;
                cycIndex++;
                if (cycIndex != 0 && cycIndex % 10 == 0) {

                    cycIndex = 0;
                    Thread.sleep(3000l);
                    System.out.println(" threadName = " + threadName + ", row-number = " + rowNum);
                }
                if (rowNum == 230) {
                    break;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {

        }
    }
}
