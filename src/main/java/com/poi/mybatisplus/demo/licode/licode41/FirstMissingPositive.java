package com.poi.mybatisplus.demo.licode.licode41;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/12
 * @Version: 1.0
 */
public class FirstMissingPositive {
    public static void main(String[] args) {
        int[] nums = {1,2,0};
        System.out.println("正确值:3-" +firstMissingPositive(nums));
        int[] nums2 = {3,4,0,2};
        System.out.println("正确值:1-" +firstMissingPositive(nums2));
        int[] nums3 = {1,2,0,3};
        System.out.println("正确值:4-" +firstMissingPositive(nums3));
        int[] nums4 = {1,2,3};
        System.out.println("正确值:4-" +firstMissingPositive(nums4));
        int[] nums5 = {3,4,-1,1};
        System.out.println("正确值:2-" +firstMissingPositive(nums5));
        int[] nums6 = {7,8,9,11,12};
        System.out.println("正确值:1-" +firstMissingPositive(nums6));
    }

    public static int firstMissingPositive(int[] nums) {

        if (nums == null) {
            return 1;
        }
        int len = nums.length;
        if (len == 0) {
            return 1;
        }
        //不需要关注的值
        int max = len + 1;
        //过滤小于0和大于数组长度的-最大值只可能等于数组长度+ 1
        for (int i = 0; i < len; i++) {
            if (nums[i] <= 0 ){
                nums[i] = max;
            }
        }
        int index ;
        for (int i = 0; i < len; i++) {
            index = Math.abs(nums[i]);//元素转化为角标
            if ( index <=  len && nums[index - 1] > 0) {
                nums[index - 1] *= -1;
            }
        }
        for (int i = 0; i < len; i++) {
             if (nums[i] > 0 ) {
                 return i + 1;
             }
        }
        return len + 1;
    }
}
