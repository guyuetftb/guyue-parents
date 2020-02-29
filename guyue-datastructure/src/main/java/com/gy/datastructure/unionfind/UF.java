package com.gy.datastructure.unionfind;

/**
 * @ClassName UF
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-01-03 14:53
 */
public interface UF {

	int getSize();

	boolean isConnected(int p, int q);

	void unionElements(int p, int q);
}
