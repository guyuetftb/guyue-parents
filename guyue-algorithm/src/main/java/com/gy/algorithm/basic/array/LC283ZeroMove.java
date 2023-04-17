package com.gy.algorithm.basic.array;


public class LC283ZeroMove {
    //解此问题，可以换个角度,从反向思考.
    //把0移动到最后,并保持数组内其他元素相对位置不变
    //含义是把所有元素移动到数组的一头,其他位置设置为0
    // 思路: 本题使用双指针来解决.
    public void moveZeroes(int[] nums) {
        int slow = 0;


        for(int fast = 0; fast < nums.length;fast++){

            if(nums[fast] == 0){
                continue;
            }

            nums[slow] =  nums[fast];

            slow++;
        }

        for(;slow < nums.length;slow++){
            nums[slow] = 0;
        }
    }
}
