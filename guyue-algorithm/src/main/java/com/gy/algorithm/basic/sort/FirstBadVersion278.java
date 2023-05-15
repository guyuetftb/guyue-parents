package com.gy.algorithm.basic.sort;

public class FirstBadVersion278 {

    public static void main(String[] args) {

    }

    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                // 此时 mid 是 bad版本, 把 mid 赋值给 right, 因为很可能当前 mid 就是每一个错误版本
                right = mid;
            } else {
                // 此时 mid 不是 bad版本, 把 mid + 1 赋值给left
                left = mid + 1;
            }
        }
        return left;
    }

    public boolean isBadVersion(int a) {
        // leetcode 系统自动实现的
        return false;
    }
}

