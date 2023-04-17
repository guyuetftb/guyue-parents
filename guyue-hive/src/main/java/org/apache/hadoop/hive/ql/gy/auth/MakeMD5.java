package org.apache.hadoop.hive.ql.gy.auth;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MakeMD5 {
    public String makeMD5(String password) {
        MessageDigest md;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 同样可以使用SHA1			// 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符			
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值			
            String pwd = new BigInteger(1, md.digest()).toString(16);
            // 参数也可不只用16可改动,当然结果也不一样了			
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public static void main(String[] args) {
        MakeMD5 md5 = new MakeMD5();
        System.out.println(md5.makeMD5("123456"));
    }
}
