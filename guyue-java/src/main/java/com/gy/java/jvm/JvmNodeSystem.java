package com.gy.java.jvm;

import org.apache.commons.lang.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * Created by lipeng
 * com.gy.java.jvm
 * lipeng
 * 2018/10/12
 */
public class JvmNodeSystem {
    public static void main(String[] args) {
        OperatingSystemMXBean mbean = ManagementFactory.getOperatingSystemMXBean();
        StringBuilder buf = new StringBuilder();
        buf.append("").append(mbean.getName()).append(' ').append(mbean.getVersion()).append(' ').append(mbean.getArch());
        buf.append(" @ ").append(mbean.getAvailableProcessors()).append(" cores");
        buf.append(" , 【 load average:").append(mbean.getSystemLoadAverage()).append(" 】");
        System.out.println(buf.toString());
    }
}
