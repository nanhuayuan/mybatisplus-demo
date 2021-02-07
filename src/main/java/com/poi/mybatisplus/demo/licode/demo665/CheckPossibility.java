package com.poi.mybatisplus.demo.licode.demo665;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/19
 * @Version: 1.0
 */
public class CheckPossibility {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        System.out.println( nums + "-正确值:true-" +checkPossibility(nums));
        int[] nums2 = {4,2,3};
        System.out.println( nums2 + "-正确值:true-" +checkPossibility(nums2));

        int[] nums3 = {4,2,1};
        System.out.println( nums3 + "-正确值:false-" +checkPossibility(nums3));

        int[] nums4 = {3,4,2,3};
        System.out.println( nums4 + "-正确值:false-" +checkPossibility(nums4));

        int[] nums5 = {5,7,1,8};
        System.out.println( nums5 + "-正确值:true-" +checkPossibility(nums5));

    }

    public static boolean checkPossibility(int[] nums) {

        /*int len = nums.length ;
        int count = 0;//统计需要换几次
        for (int i = 0; i < len - 1 ; i++){
            if(nums[i] > nums[i + 1]){
                count++;
                if (count >= 2){
                    return false;
                }
                if ((i > 0 && nums[i - 1] > nums[i + 1] ) && (i < len - 2 && nums[i] > nums[i + 2])){
                    return false;
                }
            }
        }
        return true;*/
        int len = nums.length ;
        int count = 0;
        for (int i = 0; i < len - 1 ; i++){
            if(nums[i] > nums[i + 1]){
                count++;
                if (count >= 2){
                    return false;
                }
                //下降
                if ((i > 0 && nums[i - 1] > nums[i + 1] ) || (i < len - 2 && nums[i] > nums[i + 2])){
                    return false;
                }
            }
        }
        return true;
    }
}
