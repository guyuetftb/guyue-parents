package com.gy.algorithm.basic.sort;

import java.util.Arrays;

/**
 * 闯关游戏需要破解一组密码，闯关组给出的有关密码的线索是：
 * <p>
 * 一个拥有密码所有元素的非负整数数组 password
 * 密码是 password 中所有元素拼接后得到的最小的一个数
 * 请编写一个程序返回这个密码。
 * <p>
 * 示例 1:
 * <p>
 * 输入: password = [15, 8, 7]
 * 输出: "1578"
 * 示例 2:
 * <p>
 * 输入: password = [0, 3, 30, 34, 5, 9]
 * 输出: "03033459"
 */
public class LC164BaShuZuPaiChengZuiXiaoDeShuLcof {

    public static void main(String[] args) {

    }

    public String crackPasswordQuickSort(int[] password) {
        String[] strs = new String[password.length];
        for (int i = 0; i < password.length; i++) {
            //将整数数组转换 字符串数组
            strs[i] = String.valueOf(password[i]);
        }
        //排序字符串数组
        quickSort(strs, 0, strs.length - 1);
        //拼接排序后的结果
        StringBuilder res = new StringBuilder();
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }

    void quickSort(String[] strs, int left, int right) {
        if (left >= right) {
            //下标越界
            return;
        }

        // x = 3, y = 30
        // 因为: 330 > 303 是整数大小的判断
        // 所以: x > y , 因此 y应该排在前面, x应该排在后面, 结果为-> 303
        int tmpLeft = left, tmpRight = right;
        String tmp = strs[tmpLeft];
        while (tmpLeft < tmpRight) {
            //下标不能越界, 并且
            while ((strs[tmpRight] + strs[left]).compareTo(strs[left] + strs[tmpRight]) >= 0 && tmpLeft < tmpRight) {
                tmpRight--;
            }
            while ((strs[tmpLeft] + strs[left]).compareTo(strs[left] + strs[tmpLeft]) <= 0 && tmpLeft < tmpRight) {
                tmpLeft++;
            }
            tmp = strs[tmpLeft];
            strs[tmpLeft] = strs[tmpRight];
            strs[tmpRight] = tmp;
        }
        strs[tmpLeft] = strs[left];
        strs[left] = tmp;
        quickSort(strs, left, tmpLeft - 1);
        quickSort(strs, tmpLeft + 1, right);
    }

    /**
     * 解题思路：
     * 此题求拼接起来的最小数字，本质上是一个排序问题。
     * 设数组 password 中任意两数字的字符串为 xxx 和 yyy ，则规定 排序判断规则 为：
     * <p>
     * 若 "拼接字符串" x+y > y+x, 则 x "大于" y ；
     * 反之，若 x+y<y+xx, 则 x "小于" y;
     * x "小于" y 代表：排序完成后，数组中 x 应在 y 左边；
     * x "大于" y 代表:排序完成后，数级中 x 应在  y 右边;
     * <p>
     * 链接：https://leetcode.cn/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof/solutions/190476/mian-shi-ti-45-ba-shu-zu-pai-cheng-zui-xiao-de-s-4/
     */
    public String checkPasswordInnerFunction(int[] password) {
        String[] strs = new String[password.length];
        for (int i = 0; i < password.length; i++) {
            strs[i] = String.valueOf(password[i]);
        }

        Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
        StringBuilder res = new StringBuilder();
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }
}
