package com.gy.java.common;

public class SystemArrayTest {
    public static void main(String[] args) {
        String header = "abc,"; //4
        byte[] headerBytes = header.substring(0, header.length() - 1).getBytes();

        String body = "123";    //3
        byte[] MY_SEPARATOR = "|".getBytes(); //2
        System.out.println(MY_SEPARATOR.length);

        int newByteCapacity = headerBytes.length + body.getBytes().length + MY_SEPARATOR.length;
        byte[] eventBody = new byte[newByteCapacity];

        // copy header
        System.arraycopy(header.getBytes(), 0, eventBody, 0, headerBytes.length);

        // cpoy separator
        System.arraycopy(MY_SEPARATOR, 0, eventBody, headerBytes.length, MY_SEPARATOR.length);

        // copy body
        System.arraycopy(body.getBytes(), 0, eventBody, (headerBytes.length + MY_SEPARATOR.length), body.getBytes().length);

        System.out.println(new String(eventBody));
    }
}
