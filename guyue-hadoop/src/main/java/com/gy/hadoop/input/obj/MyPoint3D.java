package com.gy.hadoop.input.obj;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.log4j.Logger;

public class MyPoint3D implements WritableComparable<MyPoint3D> {
    private static final Logger logger = Logger.getLogger(MyPoint3D.class);

    private int x;
    private int y;
    private int z;

    public MyPoint3D() {
        super();
    }

    public MyPoint3D(int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        logger.info(" \t readFields");
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        logger.info(" \t write");
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    public float distanceFromOrigin() {
        logger.info(" \t distanceFromOrigin");
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public int compareTo(MyPoint3D o) {
        logger.info(" \t compareTo");
        float myDistance = this.distanceFromOrigin();
        float otherDistance = o.distanceFromOrigin();
        return Float.compare(myDistance, otherDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return Boolean.FALSE;
        }
        if (o == this) {
            return Boolean.TRUE;
        }
        if (!(o instanceof MyPoint3D)) {
            return Boolean.FALSE;
        }
        MyPoint3D another = (MyPoint3D) o;
        return another.x == this.x && another.y == this.y
                && another.z == this.z ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" MyPoint3D [x=");
        sb.append(x);
        sb.append(",y=");
        sb.append(y);
        sb.append(",z=");
        sb.append(z);
        sb.append("]");
        return sb.toString();
    }
}
