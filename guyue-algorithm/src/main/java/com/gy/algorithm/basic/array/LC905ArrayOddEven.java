package com.gy.algorithm.basic.array;

/**
 *
 */
public class LC905ArrayOddEven {

    public static void main(String[] args) {
        int[] arr = {2, 3, 5, 22, 45, 23, 80, 97, 67, 20, 11, 25, 71, 90};

        LC905ArrayOddEven arrayOddEvenTest = new LC905ArrayOddEven();
        arrayOddEvenTest.sortArrayByParity(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public int[] sortArrayByParity(int[] arr) {
        int beginIndex = 0;
        int endIndex = arr.length - 1;

        /**
         * 要遍历数组, 所以要控制数组的索引, 不能越界
         */
        while (beginIndex < endIndex) {
            /**
             * 1. 找偶数
             * 2. 控制 beginIndex < endIndex
             * 3. 从 left 往 right 方向找 符合条件的数据
             * 4. beginIndex 位置 % 2 != 0, 是奇数, 接着往后找
             * 5. beginIndex 位置 % 2 == 0, 是偶数, 退出 小while, 到 大while
             */
            while (arr[beginIndex] % 2 != 0 && beginIndex < endIndex) {
                beginIndex++;
            }

            /**
             * 1. 找偶数
             * 2. 控制 beginIndex < endIndex
             * 3. 从 right 往 left 方向找 符合条件的数据
             * 4. beginIndex 位置 % 2 == 0, 是偶数, 接着往 前找
             * 5. beginIndex 位置 % 2 != 0, 是奇数, 退出 小while, 到 大while
             */
            while (arr[endIndex] % 2 == 0 && beginIndex < endIndex) {
                endIndex--;
            }

            int tmp = arr[beginIndex];  // beginIndex 是偶数, 要放到 后面
            arr[beginIndex] = arr[endIndex];    // endIndex 是奇数, 要放到 前面
            arr[endIndex] = tmp; // 数据互换
        }
        return arr;
    }
}