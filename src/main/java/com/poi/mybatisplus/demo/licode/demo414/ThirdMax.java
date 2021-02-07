package com.poi.mybatisplus.demo.licode.demo414;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/8
 * @Version: 1.0
 */
public class ThirdMax {
    public static void main(String[] args) {
        //int[] nums = {2, 2, 3, 1};
        int[] nums = {5,2,2};
        //int[] nums = {1,2};
        //int[] nums = {3,2,1};
        //int[] nums = {1,2,-2147483648};
        System.out.println(thirdMax2(nums));
    }

    public static int thirdMax(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return nums[0] > nums[1] ? nums[0] : nums[1];
        }

        int max1 = 0;
        int max2 = 0;
        int max3 = 0;
        int temp = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0){
                max1 = nums[i];
                count++;
                continue;
            }else if (count == 1) {
                if (max1 == nums[i]){
                    continue;
                } else {
                    count++;
                    max2 = nums[i];
                    if (max1 < max2){
                        temp = max1;
                        max1 = max2;
                        max2 = temp;
                    }
                    continue;
                }
            } else if (count == 2) {
                if (max1 == nums[i] || max2 == nums[i]){
                    continue;
                } else {
                    count++;
                    max3 = nums[i];
                    if (max2 < max3) {
                        temp = max2;
                        max2 = max3;
                        max3 = temp;
                        if (max1 < max2) {
                            temp = max1;
                            max1 = max2;
                            max2 = temp;
                        }
                    }
                    continue;
                }
            }

            if (nums[i] > max3 && nums[i] != max2 && nums[i] != max1) {
                count++;
                max3 = nums[i];
                if (max3 > max2) {
                    temp = max3;
                    max3 = max2;
                    max2 = temp;
                    if (max2 > max1){
                        temp = max2;
                        max2 = max1;
                        max1 = temp;
                    }
                }
            }
        }

        if (count >= 3) {
            return max3;
        }
        return max1 ;
    }

    //
    public static int thirdMax2(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return nums[0] > nums[1] ? nums[0] : nums[1];
        }
        //范围大了,就可以避免赋值的干扰
        long max1 = Long.MIN_VALUE;
        long max2 = Long.MIN_VALUE;
        long max3 = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > max1){
                //值往后移
                max3 = max2;
                max2 = max1;
                max1 = nums[i];
                continue;
            } else if (nums[i] == max1) {
                continue;
            } else if (nums[i]> max2) {
                max3 = max2;
                max2 = nums[i];
                continue;
            } else if (nums[i] == max2){
                continue;
            } else if (nums[i]> max3){
                max3 = nums[i];
                continue;
            }
        }
        //第三个值没有变化,意味着没有第三个值,不需要考虑是否有第二个值
        return max3 == Long.MIN_VALUE  ? (int)max1 : (int)max3;
    }

}
