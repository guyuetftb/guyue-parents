package com.gy.offer.string;

/**
 * @ClassName RepleaseSpace5
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-03-13 21:16
 */
public class RepleaseSpace5 {


	public static void replaceSpace(char[] newData, int srcDataLength) {

		/**
		 * 1. 先循环一次,找出所有空格个数.
		 * 2. 替换后的长度=原长度 + 空格个数 * 2
		 */
		int newDataLength = srcDataLength;
		for (int i = 0; i < srcDataLength; i++) {
			if (newData[i] == ' ') {
				newDataLength += 2;
			}
		}

		for (
			// 数组位置 = 数组长度减1
			int indexOfOld = srcDataLength - 1, indexOfNew = newDataLength - 1;
			// indexOfOld, indexOfNew 不能小于0, 并且两者不能相等.
			indexOfOld >= 0 && indexOfNew >= 0 && indexOfOld != indexOfNew;
			// 两个指针不断的前移.
			indexOfNew--, indexOfOld--
		) {
			/**
			 * 以旧数组指针为轴, 向前遍历, 处理空格字符串.
			 */
			if (newData[indexOfOld] == ' ') {
				// indexOfNew--
				// 先赋值操作
				// 再对indexOfNew减1.
				newData[indexOfNew--] = '0';
				newData[indexOfNew--] = '2';
				newData[indexOfNew] = '%';
			} else {
				newData[indexOfNew] = newData[indexOfOld];
			}
		}
	}

	public static void main(String[] args) {
		char[] srcdata = "we are happy.".toCharArray();
		char[] data = new char[20];
		for (int i = 0; i < srcdata.length; i++) {
			data[i] = srcdata[i];
		}
		System.out.println(srcdata);

		replaceSpace(data, srcdata.length);
		System.out.print(data);
	}
}

