import java.util.Base64;

public class Test {

    public static void main(String[] args) {
        String longString = "[29990]:ClassJavaLaunchHelperisimplemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin/java";
        String longString2 = "1725722917850976256";
        encode(longString);
        encode(longString2);
    }

    public static void encode(String longString){

        byte[] encoded = Base64.getEncoder().encode(longString.getBytes());
        String shortString = new String(encoded); // 取前8位作为短字符串

        System.out.println("长字符串：" + longString);
        System.out.println("短字符串：" + shortString);
        byte[] decode = Base64.getDecoder().decode(shortString);
        String newString = new String(decode);
        System.out.println("还原符串：" + newString);
    }
}

