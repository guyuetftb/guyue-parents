package com.gy.algorithm.basic.search.binary;

/**
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
 * 例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [3,4,5,1,2]
 * 输出：1
 * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
 * <p>
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2]
 * 输出：0
 * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 3 次得到输入数组。
 * <p>
 * 示例 3：
 * 输入：nums = [11,13,15,17]
 * 输出：11
 * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
 */
public class LC153FindMinimumInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 5, 1, 2};
        LC153FindMinimumInRotatedSortedArray lc153FindMinimumInRotatedSortedArray = new LC153FindMinimumInRotatedSortedArray();
        int min = lc153FindMinimumInRotatedSortedArray.findMin(nums);
        System.out.println(min);
    }

    public int findMin_ME(int[] nums) {
        return 0;
    }

    /**
     * 作者：力扣官方题解
     * 链接：https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/solutions/698479/xun-zhao-xuan-zhuan-pai-xu-shu-zu-zhong-5irwp/
     * <p>
     * 一个不包含重复元素的升序数组在经过旋转之后，可以得到下面可视化的折线图：LC153FindMinimumInRotatedSortedArray-1.png
     * 其中横轴表示数组元素的下标，纵轴表示数组元素的值。图中标出了最小值的位置，是我们需要查找的目标。
     * <p>
     * 我们考虑数组中的最后一个元素 x：
     * --> 在最小值右侧的元素（不包括最后一个元素本身），它们的值一定都严格小于 x；
     * --> 而在最小值左侧的元素，它们的值一定都严格大于 x。因此，我们可以根据这一条性质，通过二分查找的方法找出最小值。
     * <p>
     * 在二分查找的每一步中，左边界为 low，右边界为 high，区间的中点为 pivot，最小值就在该区间内。
     * 我们将中轴元素 nums[pivot] 与右边界元素 nums[high] 进行比较，可能会有以下的三种情况：
     * <p>
     * 第一种情况是 nums[pivot] < nums[high]。
     * 如下图所示：LC153FindMinimumInRotatedSortedArray-2.png，这说明 nums[pivot] 是最小值右侧的元素，因此我们可以忽略二分查找区间的右半部分。
     * <p>
     * 第二种情况是 nums[pivot]>nums[high]。
     * 如下图所示：LC153FindMinimumInRotatedSortedArray-3.png，这说明 nums[pivot]是最小值左侧的元素，因此我们可以忽略二分查找区间的左半部分。
     * <p>
     * 由于数组不包含重复元素，并且只要当前的区间长度不为 1，pivot 就不会与 high重合；
     * 而如果当前的区间长度为 1，这说明我们已经可以结束二分查找了。
     * 因此不会存在 nums[pivot]=nums[high] 的情况。
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        //数组不包含重复元素,且长度不为1
        //数据是按升降旋转N次后得到
        //low等于第1个元素的下标
        int low = 0;
        //high 等于最后一个元素下标
        int high = nums.length - 1;
        while (low < high) {
            //确定中间元素
            //等同于int pivot = (high + low) / 2; 即找中间位置，但是low + (high - low) / 2这样写可以防止类型范围溢出
            int pivot = low + (high - low) / 2;

            //这种情况是: 中间元素 小于(<) 右边的元素, 说明最小值在 中间元素的左边
            if (nums[pivot] < nums[high]) {
                high = pivot;
            }
            //这种情况是: 中间元素 大于(>) 右边的元素, 说明最小值 在中间元素的左边
            else {
                low = pivot + 1;
            }
        }
        return nums[low];
    }


    /**
     * 链接：https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/solutions/2576214/bi-jiao-rong-yi-li-jie-de-fang-fa-dai-ma-1yaj/
     */
    public int findMin_1(int[] nums) {
        int point = -1;  //拐点
        int first = nums[0];  //第一个数
        int left = 0;
        int right = nums.length - 1;

        //二分法
        while (left <= right) {
            int mid = (left + right) / 2;
            int value_mid = nums[mid];
            /**
             * mid值等于first有两种情况:
             * 1.数组只有一个元素，此时first=value_mid,令left=mid+1可结束循环
             * 2.数组只有两个元素，此时mid刚好是第一个元素的下标，此时令left=mid+1可进入下一个循环，跳过first元素
             */
            if (value_mid == first) {
                left = mid + 1;
            }
            //比first小,记录下来然后继续往左找
            else if (value_mid < first) {
                point = mid;
                right = mid - 1;
            }
            //比first大,记录下来然后继续往右找
            else if (value_mid > first) {
                left = mid + 1;
            }
        }

        /**
         * 由旋转的定义可知，旋转后的拐点就是最小的,例如
         * [3,4,5,1,2] 1是拐点，也是最小的如果找到了拐点，
         * 则返回拐点元素如果没找到拐点，则说明数组没有旋转，返回第一个元素
         */
        return point != -1 ? nums[point] : first;
    }
}
