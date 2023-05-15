package com.gy.algorithm.basic.sort;

public class BinarySearch {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int key = 3;
        System.out.println(binarySearch(a, 0, a.length - 1, key));
    }

    public static int binarySearch(int[] a, int left, int right, int key) {

        // 左侧下标 大于 右侧下标
        if (left > right) {
            return -1;
        }

        while (left < right) {
            // 不能直接使用 mid = (left + right) / 2, 因为在 left ,right 很大的时候, 会内存溢出
            int mid = left + (right - left) / 2;

            // 找到的元素正好等于 key
            if (key == a[mid]) {
                return mid;
            } else if (key < a[mid]) {
                // 中间元素大, key 元素在中间元素的左侧
                return binarySearch(a, left, mid - 1, key);
            } else if (key > a[mid]) {
                // 中间元素小, key 元素在中间元素的右侧
                return binarySearch(a, mid + 1, right, key);
            }
        }
        return -1;
    }
}
