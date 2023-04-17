package com.gy.algorithm.basic.array;

public class LC88MergeTwoArrayIntoOneArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int one1 = m - 1;
        int two2 = n - 1;

        int cur = nums1.length - 1;
        // 通过循环把 num2 的元素都移动到 num1 中
        // 所以主循环num2
        while (two2 >= 0) {
            // 比较2个组数中的元素.
            // 第2个数组末尾元素，大于第1个数组末尾
            // 为了防止越界 one1 必须是大于等于 0
            /**
             * 注意: 判断条件写反了, 容易引发数组下标越界异常.
             */
            if (one1 >= 0 && nums1[one1] > nums2[two2]) {

                // 把 第2个数组的元素放到 第1个数组未尾
                nums1[cur] = nums1[one1];

                // 第2个索引递减
                one1--;

                // 当前索引递减
                cur--;
            } else {
                // 把第1个数组的元素放到 第1个数组末尾
                nums1[cur] = nums2[two2];

                // 第1个索引递减
                two2--;

                // 当前索引递减
                cur--;
            }
        }

        /**
         * int one1 = m - 1;
         * int two2 = n - 1;
         * int eleIndex = m + n - 1;
         * while (two2 >= 0) {
         * while(two2 >= 0) 满足条件时, if 条件判断 逻辑有可能在某些条件下不会执行
         * 从而无法结束循环, eleIndex, one1 的值会一直 减下去, 造成 数组下标越界.
         *   if (one1 >= 0 && nums2[two2] > nums1[one1]) {
         *                 nums1[eleIndex] = nums2[two2];
         *                 two2--;
         *                 eleIndex--;
         *             } else {
         *                 nums1[eleIndex] = nums1[one1];
         *                 one1--;
         *                 eleIndex--;
         *             }
         *         }
         */
    }
}
