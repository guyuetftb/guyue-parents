package com.gy.algorithm.basic.doublepoints;

public class SqrtX69 {

    public static void main(String[] args) {

    }

    public int mySqrt(int x) {
        int leftPoint = 0;
        int rightPoint = x;

        int ans = -1;

        while (leftPoint <= rightPoint) {
            int mid = leftPoint + (rightPoint - leftPoint) / 2;

            if ((long) mid * mid <= x) {
                ans = mid;
                leftPoint = mid + 1;
            } else {
                rightPoint = mid - 1;
            }
        }
        return ans;
    }
}
