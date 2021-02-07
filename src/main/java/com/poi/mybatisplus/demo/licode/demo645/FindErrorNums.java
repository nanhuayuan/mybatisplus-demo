package com.poi.mybatisplus.demo.licode.demo645;

import java.util.HashSet;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/8
 * @Version: 1.0
 */
public class FindErrorNums {
    public static int[] findErrorNums(int[] nums) {

        HashSet<Integer> hashSet = new HashSet<>();
        //多余的数
        int redundant = 0;
        //数组的和
        int arrSum = 0;
        //1-n的顺序和
        int sequenceSum = nums.length * (nums.length + 1) / 2;
        for (int num : nums) {
            if (!hashSet.add(num)){
                redundant = num;
            }
            arrSum += num;
        }
        //少了的数 顺序和-数组和-重复(数组和多减部分)
        int less = sequenceSum - arrSum + redundant;
        return new int[]{redundant, less};
    }

    public static void main(String[] args) {
        int[] nums = {1,2,2,4};
        System.out.println(findErrorNums(nums));
    }

}
