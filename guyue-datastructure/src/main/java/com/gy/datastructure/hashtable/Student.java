package com.gy.datastructure.hashtable;

public class Student {

    int grade;
    int cls;
    String firstName;
    String lastName;

    Student(int grade, int cls, String firstName, String lastName){
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int hashCode(){

        // 对复合类型求 hash 值，就可以采用上面我们提到的方法.
        // 1. 自定义一个 B
        int B = 31;
        // 2. 初始化一个 hash 值.
        int hash = 0;

        // 3. 针对每个部分分别计算 HashCode 值，然后不断累加
        hash = hash * B + ((Integer)grade).hashCode();
        hash = hash * B + ((Integer)cls).hashCode();
        hash = hash * B + firstName.toLowerCase().hashCode();
        hash = hash * B + lastName.toLowerCase().hashCode();

        // 4. 上面的处理可能会引发 整型的溢出，即 hashCode 值会超过整型的最大值
        //    但是由于计算机特性, 当对整型的最大值再加1时，整型的值就会变成最小值，
        //    hashCode 的值可能不是真实的数值，但是不会引发程序报错，也是满足hashCode 语意的。

        // 5. 这里我重写了 hashCode 方法，其实如果我们不重写 hashCode 方法的话
        // 我们也可以得到一个 hashCode 值, 这个值是在Object对象里，根据对象的地址计算出来的.

        // 6. 这里虽然我们已经解决了 hashCode生成的问题，但是当遇到 hash 冲突的时候该怎么处理呢?
        //    这就需要用到 equals 方法了, 当遇到 hash 冲突的时候, 需要比较2个对象是否一致，只有同时覆盖了
        //    hashCode 和 equals 方法，才能安心在 Java 集合中添加我们自定义的对象.
        return hash;
    }

    @Override
    public boolean equals(Object o){

        if(this == o)
            return true;

        if(o == null)
            return false;

        if(getClass() != o.getClass())
            return false;

        Student another = (Student)o;
        return this.grade == another.grade &&
                this.cls == another.cls &&
                this.firstName.toLowerCase().equals(another.firstName.toLowerCase()) &&
                this.lastName.toLowerCase().equals(another.lastName.toLowerCase());
    }
}
