package com.gy.algorithm.basic.search.binary;

/**
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * <p>
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * <p>
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 */
public class LC33SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        nums = new int[]{0, 1, 2, 4, 5, 6, 7};
        int target = 0;
        LC33SearchInRotatedSortedArray lc33SearchInRotatedSortedArray = new LC33SearchInRotatedSortedArray();
        int search = lc33SearchInRotatedSortedArray.search(nums, target);
        System.out.println(search);
    }

    public int search(int[] nums, int target) {
        return search_method2(nums, target);
    }

    /**
     * 待改进
     *
     * @param nums
     * @param target
     * @return
     */
    public int search_ME(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = -1;
        while (left < right) {
            mid = left + (right - left) / 2;
            // 数组右侧的元素都大于等于mid元素,忽略右侧元素
            if (nums[mid] < target) {
                right = mid;
            }
            // 数组左侧元素都小于等于Mid元素,忽略左侧元素
            else if (nums[mid] > target) {
                left = mid;
            }
            // 找到目标元素
            else {
                return mid;
            }
        }
        return mid;
    }


    /**
     * 思路一：将 「旋转数组查找目标值」 转化成 「有序数组查找目标值」
     * 方法一：最简单的做法, 先找到最值将旋转数组分成两段有序数组，接下来在有序数组中找目标值就轻车熟路了。
     * 先找到 「153. 寻找旋转排序数组中的最小值」的索引，由此可以将数组分为升序的两段。
     * 根据 nums[0] 与 target 的关系判断 target 在左段还是右段，再对升序数组进行二分查找即可。
     * 同样的思路可以解决「1095. 山脉数组中查找目标值」，即先找到山脉数组的峰顶「852. 山脉数组的峰顶索引」, 通过峰顶将山脉数组分为两段有序的数组，接下来就可以在有序数组中查找目标值了。
     * 代码比较简单就不贴了。
     * <p>
     * <p>
     * 方法二：在 英文版 看到了一个有意思的思路。
     * 对于旋转数组 nums = [4,5,6,7,0,1,2]
     * 首先根据 nums[0] 与 target 的关系判断 target 是在左段还是右段。
     * 因为数组是升序的, target 与 nums[0] 的关系是 大于等于 >= 的。
     * 假设数组不排序, target必然大于等于 nums[0], 否则 target就不存在了.
     * 所以: 如果 target 大于等于 num[0] 旋转之后, 要找的target就在左半断, 因为大值被旋转到前面了.
     * 所以: 如果 target 小于等于 num[0] 旋转之后, 要找的target就在右半断, 因为小值被旋转走了.
     * <p>
     * 例如 target = 5, 目标值在左半段，因此在 [4, 5, 6, 7, inf, inf, inf] 这个有序数组里找就行了；
     * 例如 target = 1, 目标值在右半段，因此在 [-inf, -inf, -inf, -inf, 0, 1, 2] 这个有序数组里找就行了。
     * 如此，我们又将「旋转数组中找目标值」 转化成了 「有序数组中找目标值」
     * <p>
     * <p>
     * 链接：https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/221435/duo-si-lu-wan-quan-gong-lue-bi-xu-miao-dong-by-swe/
     */
    public int search_method2(int[] nums, int target) {
        // https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/221435/duo-si-lu-wan-quan-gong-lue-bi-xu-miao-dong-by-swe/
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            //先取中间坐标位置
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                //相等就直接返回
                return mid;
            }

            /**
             * 首先根据 nums[0] 与 target 的关系判断 target 是在左段还是右段。
             * 因为数组是升序的, target 与 nums[0] 的关系是 大于等于 >= 的。
             * 假设数组不排序, target必然大于等于 nums[0], 否则 target就不存在了.
             * 所以: 如果 target 大于等于 num[0] 旋转之后, 要找的target就在左半断, 因为大值被旋转到前面了.
             * 所以: 如果 target 小于等于 num[0] 旋转之后, 要找的target就在右半断, 因为小值被旋转走了.
             *
             * // 先根据 nums[0] 与 target 的关系判断目标值是在左半段还是右半段
             */
            if (target >= nums[0]) {
                // 目标值在左半段时，若 mid 在右半段，则将 mid 索引的值改成 inf
                if (nums[mid] < nums[0]) {
                    nums[mid] = Integer.MAX_VALUE;
                }
            } else {
                // 目标值在右半段时，若 mid 在左半段，则将 mid 索引的值改成 -inf
                if (nums[mid] >= nums[0]) {
                    nums[mid] = Integer.MIN_VALUE;
                }
            }

            // mid 值小于 target值, 在右侧. 改变lo的值
            if (nums[mid] < target) {
                lo = mid + 1;
            }
            // mid 值大于 target值, 在左侧. 改变hi的值.
            else {
                hi = mid - 1;
            }
        }
        return -1;
    }
}
