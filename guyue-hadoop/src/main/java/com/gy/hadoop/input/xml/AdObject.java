package com.gy.hadoop.input.xml;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.log4j.Logger;

public class AdObject implements WritableComparable<AdObject> {
    private static final Logger logger = Logger.getLogger(AdObject.class);

    private String title;
    private String url;
    private String id;

    public AdObject() {
        super();
    }

    public AdObject(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        if (null != out) {
            logger.info("AdObject.write");
            Text.writeString(out, id);
            Text.writeString(out, url);
            Text.writeString(out, title);
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        if (null != in) {
            logger.info("AdObject.readFields");
            id = Text.readString(in);
            url = Text.readString(in);
            title = Text.readString(in);
        }
    }

    @Override
    public int compareTo(AdObject o) {
        /**
         * 
         */
        logger.info("AdObject.compareTo");
        return id.compareTo(o.getId());
    }

    public void set(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        /**
         * 最后结束的输出,就是调用 value类的string方法
         */
        logger.info("AdObject.toString");
        return "title=" + title + ", url=" + url + ", id=" + id;
    }

}
