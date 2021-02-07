package com.poi.mybatisplus.demo.licode.demo628;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/8
 * @Version: 1.0
 */
public class MaximumProduct {
    public static void main(String[] args) {
        //int[] nums = {2, 2, 3, 1};
        //int[] nums = {5,2,2};
        //int[] nums = {1,2};
        //int[] nums = {3,2,1};
        //int[] nums = {-100,-98,-1,2,3,4};
        //int[] nums = {-8,-7,-2,10,20};
        int[] nums = {-1,-2,-3,-4};
        System.out.println(maximumProduct(nums));
    }

    public static int maximumProduct(int[] nums) {
        if (nums.length == 3) {
            return nums[0] * nums[1] * nums[2];
        }
        //范围大了,就可以避免赋值的干扰
        int max1 = 0;
        int max2 = 0;
        int max3 = 0;

        int min1 = 0;
        int min2 = 0;
        int min3 = 0;

        long max01 = Long.MIN_VALUE;
        long max02 = Long.MIN_VALUE;
        long max03 = Long.MIN_VALUE;

        int negativeCount = 0;
        int positiveCount = 0;
        boolean hasZero = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0){
                positiveCount++;
                if(nums[i] >= max1){
                    //值往后移
                    max3 = max2;
                    max2 = max1;
                    max1 = nums[i];
                    continue;
                } else if (nums[i] >= max2) {
                    max3 = max2;
                    max2 = nums[i];
                    continue;
                } else if (nums[i] > max3){
                    max3 = nums[i];
                    continue;
                }
            } else if (nums[i] == 0){
                hasZero = true;
            } else {
                negativeCount++;
                if(nums[i] <= min1){
                    //值往后移
                    min3 = min2;
                    min2 = min1;
                    min1 = nums[i];
                } else if (nums[i] <= min2) {
                    min3 = min2;
                    min2 = nums[i];
                } else if (nums[i] < min3){
                    min3 = nums[i];
                }


                if(nums[i] > max01){
                    //值往后移
                    max03 = max02;
                    max02 = max01;
                    max01 = nums[i];
                } else if (nums[i] > max02) {
                    max03 = max02;
                    max02 = nums[i];
                    continue;
                } else if (nums[i]> max03){
                    max03 = nums[i];
                    continue;
                }

            }
        }
        //正数0个 全部负数
        if (positiveCount == 0){
            if (hasZero) {
                return 0;
            }
            return (int)(max01 * max02 * max03);
        }

        //正数1个
        if (positiveCount == 0){
            return (int)(max01 * min1 * min2);
        }
        //两个正数
        if (positiveCount == 2){
            if (negativeCount >= 2){
                return (int)(max1 * min1 * min2);
            }
            if (hasZero) {
                return 0;
            }
            return (int)(max1 * max2 * max01);
        }
        //全部正数 最大的负数等于0
        if (negativeCount == 0) {
            return max1 * max2 * max3;
        }

        //多个正数 多个负数
        return max1 * max2 * max3 > max1 * min1 * min2 ? (int)(max1 * max2 * max3) : (int)(max1 * min1 * min2);
    }

    //官方
    //最终的选择存在以下四种情况：
    // 1.三个数字全部为整数，结果肯定为最大的三个正数的积； 最大三个
    // 2.三个数字有两个为正数，这种情况结果为负数，说明只有两个正数，且负数的数量也不可能多于一个，可以证明此时只有三个数字，可以认为是选择最大的三个数； 最大三个
    // 3.三个数字中有一个为正数，这时，我们很显然会选择两个最小的负数和一个最大的正数；  最大正数,最小两个数
    // 4.三个数字全部为负数，这种情况下，所有数字都为负数，我们显然选择最大的三个数。 最大三个数
    public int maximumProduct2(int[] nums) {
        /*排序法：数组可以分为三种情况，第一是都为正数，第二是都为负数，第三是有正有负（分为（1）只有一个负数（2）有两个及以上的负数）
        都为正数：乘积最大值为排序数组最后三个数相乘
        都为负数：乘积最大值为排序数组最后三个数相乘
        有正有负：（1）乘积最大值为排序数组最后三个数相乘
                （2）乘积最大值为排序数组前两个负数与数组最后一个正数相乘
        整理一下上面的四种情况：**可以归纳成取max（排序数组最后三个数相乘，排序数组前两个负数与数组最后一个正数相乘）
        不排序方法：通过上面对排序法的分析，我们可以看出，实际上我们只要找到数组的第一大的值，第二大的值，第三大的值，第一小的值和第 二小的值即可。所以我们只需要遍历一边数组，即可找到这些值（具体实现看代码注释）！*/
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for (int n: nums) {
            if (n <= min1) {            //啊？你比第一小的值还小，哈哈哈，笑死我了，来来来，快去！
                min2 = min1;            //原第一小，恭喜你，终于找到比你小的了，你现在是第二小！
                min1 = n;               //老实呆着，你现在是最小的了！！！
            } else if (n <= min2) {     // 哦？你比第二小的值小？比最小的还大，嗯..那你去做第二小
                min2 = n;               // # 来吧，你现在是第二小，原第二小解脱了！
            }
            if (n >= max1) {            //  啥？你比第一大的值还大？？那好吧，你去做老大
                max3 = max2;            //  原老二委屈一下你，去做老三吧，难受..
                max2 = max1;            //  原老大委屈一下你，去做老二吧，很难受...
                max1 = n;               //  大哥快请上座！！！
            } else if (n >= max2) {     // 嗯？你比第二大的值大啊？？那行吧，老二给你做，别碰老大啊，他脾气不好...
                max3 = max2;            //原老二委屈一下你，去做老三吧，难受...
                max2 = n;               //二哥请上座！！
            } else if (n >= max3) {     // n lies betwen max2 and max3
                max3 = n;               //三哥上座！
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }

}
