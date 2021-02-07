package com.poi.mybatisplus.demo.licode.demo448;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/11
 * @Version: 1.0
 */
public class FindDisappearedNumbers {
    public static void main(String[] args) {
        //int[] nums = {1,0,1,1,0,1};
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDisappearedNumbers(nums));
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {

        List<Integer>  result = new ArrayList();
        int e = 0;
        for (int i = 0; i < nums.length; i++) {
            e = Math.abs(nums[i]);//元素
            if (nums[e - 1] > 0) {
                nums[e - 1] *= -1;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i+ 1);
            }
        }
        return result;
    }
}
