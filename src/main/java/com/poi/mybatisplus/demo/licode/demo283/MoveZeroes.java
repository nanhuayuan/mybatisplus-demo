package com.poi.mybatisplus.demo.licode.demo283;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/20
 * @Version: 1.0
 */
public class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = {0,1,0,3,12};
        moveZeroes(nums);
        int[] nums2 = {1,0,1};
        moveZeroes(nums2);
    }

    public static void   moveZeroes(int[] nums) {

        int zStart = -1;
        int len = nums.length ;

        for (int i = 0; i < len  ; i++){
            if (nums[i] != 0 ){
                //前面没有0,下一次
                if(zStart < 0){
                    continue;
                }
                //往前移
                nums[zStart] = nums[i];
                nums[i] = 0;
                zStart++;
            } else {
                if (zStart < 0){
                    zStart = i;
                }
            }
        }
        System.out.println(nums);
    }
}
