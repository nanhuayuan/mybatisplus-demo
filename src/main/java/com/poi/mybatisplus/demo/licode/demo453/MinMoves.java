package com.poi.mybatisplus.demo.licode.demo453;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/18
 * @Version: 1.0
 */
public class MinMoves {
    public static void main(String[] args) {
       /* int[] nums = {1,2,3};
        System.out.println( nums + "-正确值:3-" +minMoves(nums));
        int[] nums2 = {100};
        System.out.println( nums2 + "-正确值:1-" +minMoves(nums2));*/

        int[] nums3 = {-100,0,100};
        System.out.println( nums3 + "-正确值:1-" +minMoves(nums3));
    }

    public static int minMoves(int[] nums) {
        int len = nums.length;
        if (len == 1){
            return 0;
        }
        int max = nums[0];
        //被引用最少的次数
        int min = nums[0];
        //共计被引用的次数
        int sum = 0;
        for (int i = 0; i < len; i++){
            int num = nums[i];

            if(num < min){
                min = num;
            } else if (num > max) {
                max = num;
            }
            sum += num;
        }
        int destSum = max * len;
        //int destAdd = 0;//目标加了多少次
        int add = max - min;//加了几次
        int step = len - 1;//步长
        sum = sum + add * step;
        int destAdd = sum > destSum ? (sum - destSum) / len : 0;
        destSum += len * destAdd;
        while (destSum != sum){
            if(destSum > sum){
                add++;
                sum += step;
            } else {
                destSum += len;
            }
        }
        return add;
    }
}
