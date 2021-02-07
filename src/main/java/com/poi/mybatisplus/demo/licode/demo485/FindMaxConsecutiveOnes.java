package com.poi.mybatisplus.demo.licode.demo485;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/7
 * @Version: 1.0
 */
/*        给定一个二进制数组， 计算其中最大连续1的个数。

        示例 1:

        输入: [1,1,0,1,1,1]
        输出: 3
        解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.

        注意：

        输入的数组只包含 0 和1。
        输入数组的长度是正整数，且不超过 10,000。*/


public class FindMaxConsecutiveOnes {

    public static int findMaxConsecutiveOnes(int[] nums) {

        int max = 0;
        int count = 0;
        boolean start = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1  ) {
                if (!start){
                    start = true;
                    count = 1;
                } else {
                    count++;
                }
                max = count > max ? count : max;
            } else {
                start = false;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        //int[] nums = {1,0,1,1,0,1};
        int[] nums = {1};
        System.out.println(findMaxConsecutiveOnes(nums));
    }
}
