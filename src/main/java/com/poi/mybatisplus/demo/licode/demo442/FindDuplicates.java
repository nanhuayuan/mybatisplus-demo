package com.poi.mybatisplus.demo.licode.demo442;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/11
 * @Version: 1.0
 */
public class FindDuplicates {
    public static void main(String[] args) {
        //int[] nums = {1,0,1,1,0,1};
        //int[] nums = {4,3,2,7,8,2,3,1};
        //int[] nums = {10,2,5,10,9,1,1,4,3,7};
        int[] nums = {5,4,6,7,9,3,10,9,5,6};
        System.out.println(findDuplicates2(nums));
    }

    public static List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList();
        int index = 0;
        int leng = nums.length;
        for (int i = 0; i < leng; i++) {
            index = Math.abs(nums[i]) - 1;//元素转化为角标
            if ( nums[index] > 0) {
                nums[index] *= -1;
            } else {
                result.add( index + 1);
            }
        }
        return result;
    }


    public static List<Integer> findDuplicates2(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return res;
        }
        for (int i = 0; i < len; i++) {
            while (nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] - 1 != i) {
                res.add(nums[i]);
            }
        }
        return res;
    }

    private static void swap(int[] nums, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        nums[index1] = nums[index1] ^ nums[index2];
        nums[index2] = nums[index1] ^ nums[index2];
        nums[index1] = nums[index1] ^ nums[index2];
    }
}
