package com.gy.algorithm.basic.search.binary;

/**
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
 * 例如，原数组 nums = [0,1,4,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
 * 若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * <p>
 * 给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，
 * 并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * 你必须尽可能减少整个过程的操作步骤。
 * <p>
 * <p>
 * 示例 1：
 * 输入：nums = [1,3,5]
 * 输出：1
 * <p>
 * 示例 2：
 * 输入：nums = [2,2,2,0,1]
 * 输出：0
 */
public class LC154FindMinimumInRotatedSortedArrayII {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 2, 0, 1};
        LC153FindMinimumInRotatedSortedArray lc153FindMinimumInRotatedSortedArray = new LC153FindMinimumInRotatedSortedArray();
        int min = lc153FindMinimumInRotatedSortedArray.findMin(nums);
        System.out.println(min);
    }

    public int findMin_ME(int[] nums) {
        return 0;
    }

    /**
     * 一个包含重复元素的升序数组在经过旋转之后，可以得到下面可视化的折线图：LC154FindMinimumInRotatedSortedArrayII-1.png
     * <p>
     * 其中横轴表示数组元素的下标，纵轴表示数组元素的值。图中标出了最小值的位置，是我们需要查找的目标。
     * <p>
     * 我们考虑数组中的最后一个元素 x：在最小值右侧的元素，它们的值一定都小于等于 (<=) x；
     * 而在最小值左侧的元素，它们的值一定都大于等于 (>=) x。
     * 因此，我们可以根据这一条性质，通过二分查找的方法找出最小值。
     * <p>
     * 在二分查找的每一步中，左边界为 low，右边界为 high，区间的中点为 pivot，最小值就在该区间内。
     * 我们将中轴元素 nums[pivot] 与右边界元素 nums[high] 进行比较，可能会有以下的三种情况：
     * <p>
     * 第一种情况是 nums[pivot]<nums[high]。
     * 如下图所示，这说明 nums[pivot] 是最小值右侧的元素，因此我们可以忽略二分查找区间的右半部分。LC154FindMinimumInRotatedSortedArrayII-2.png
     * <p>
     * 第二种情况是 nums[pivot]>nums[high]。
     * 如下图所示，这说明 nums[pivot] 是最小值左侧的元素，因此我们可以忽略二分查找区间的左半部分。LC154FindMinimumInRotatedSortedArrayII-3.png
     * <p>
     * 第三种情况是 nums[pivot]==nums[high]。LC154FindMinimumInRotatedSortedArrayII-4.png
     * 如下图所示，由于重复元素的存在，我们并不能确定 nums[pivot] 究竟在最小值的左侧还是右侧，因此我们不能莽撞地忽略某一部分的元素。
     * 我们唯一可以知道的是，由于它们的值相同，所以无论 nums[high] 是不是最小值，都有一个它的「替代品」nums[pivot]，
     * 因此我们可以忽略二分查找区间的右端点。
     * 当二分查找结束时，我们就得到了最小值所在的位置。
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                // mid元素都小于等于 最right元素
                high = pivot;
            } else if (nums[pivot] > nums[high]) {
                // mid元素都大于等于 最left元素
                low = pivot + 1;
            } else {
                //因为有重复元素在,所以不能忽略任何一边,mid == high, 所以只能缩小范围
                high -= 1;
            }
        }
        return nums[low];
    }
}
