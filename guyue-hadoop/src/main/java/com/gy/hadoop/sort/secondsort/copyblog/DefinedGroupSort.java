package com.gy.hadoop.sort.secondsort.copyblog;

/**
 * Created by lipeng on 1/14/18.
 */

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义分组策略
 * 将组合将中第一个值相同的分在一组
 *
 * @author zengzhaozheng
 */
public class DefinedGroupSort extends WritableComparator {
    private static final Logger logger = LoggerFactory.getLogger(DefinedGroupSort.class);

    public DefinedGroupSort() {
        super(CombinationKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        logger.info("-------enter DefinedGroupSort flag-------");
        CombinationKey ck1 = (CombinationKey) a;
        CombinationKey ck2 = (CombinationKey) b;
        logger.info("-------Grouping result:" + ck1.getFirstKey().
                compareTo(ck2.getFirstKey()) + "-------");
        logger.info("-------out DefinedGroupSort flag-------");
        return ck1.getFirstKey().compareTo(ck2.getFirstKey());
    }
}
