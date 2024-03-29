/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package com.gy.avro.login;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface UserRpc {
    public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"UserRpc\",\"namespace\":\"com.gy.avro.login\",\"types\":[{\"type\":\"record\",\"name\":\"UserRequest\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"password\",\"type\":\"string\"},{\"name\":\"devicetoken\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"UserResponse\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"loginToken\",\"type\":\"string\"}]}],\"messages\":{\"send\":{\"request\":[{\"name\":\"request\",\"type\":\"UserRequest\"}],\"response\":\"UserResponse\"}}}");

    /**
     */
    com.gy.avro.login.UserResponse send(com.gy.avro.login.UserRequest request) throws org.apache.avro.AvroRemoteException;

    @SuppressWarnings("all")
    public interface Callback extends UserRpc {
        public static final org.apache.avro.Protocol PROTOCOL = com.gy.avro.login.UserRpc.PROTOCOL;

        /**
         * @throws java.io.IOException The async call could not be completed.
         */
        void send(com.gy.avro.login.UserRequest request, org.apache.avro.ipc.Callback<com.gy.avro.login.UserResponse> callback) throws java.io.IOException;
    }
}