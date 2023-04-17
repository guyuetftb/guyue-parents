package com.gy.datastructure.segmenttree;

/**
 * @ClassName Merger
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-01 20:42
 */
public interface Merger<E> {

	E merge(E a, E b);
}
