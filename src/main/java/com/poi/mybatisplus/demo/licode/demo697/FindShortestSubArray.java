package com.poi.mybatisplus.demo.licode.demo697;

import java.util.*;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/11
 * @Version: 1.0
 */
public class FindShortestSubArray {
    public static void main(String[] args) {
        int[] nums = {1,2,1,2,4,4};
        System.out.println(findShortestSubArray3(nums));
    }

    public static int findShortestSubArray(int[] nums) {

        if (nums.length == 1) {
            return 1;
        }
        Map<Integer, List<Integer>> elementMap = new HashMap<>();
        //统计每个元素出现的位置
        int size = nums.length;
        int element = 0;
        int max = 0;
        for (int i = 0; i < size; i++) {
            element = nums[i];
            if(elementMap.containsKey(element)){
                List<Integer> elIntegers = elementMap.get(element);
                elIntegers.add(i);

                if (elIntegers.size() > max) {
                    max = elIntegers.size();
                }
            }else {
                List<Integer> index = new ArrayList<>();
                index.add(i);
                elementMap.put(element, index);
                if (max < 1) {
                    max = 1;
                }
            }
        }

        /*for (List<Integer> value : elementMap.values()) {
            if (value.size() > max) {
                max = value.size();
            }
        }*/

        if (max == 1) {
            return 1;
        }
        int minSize = Integer.MAX_VALUE;
        int elementMinSize;
        for (Map.Entry<Integer, List<Integer>> entry : elementMap.entrySet()) {
            //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            List<Integer> value = entry.getValue();
            int elementNum = value.size();
            if (max == elementNum){
                elementMinSize = value.get(elementNum - 1) - value.get(0) + 1;
                if (minSize >= elementMinSize) {
                    minSize = elementMinSize;
                }
            }
        }
        return minSize;
    }


    public static int findShortestSubArray2(int[] nums) {

        if (nums.length == 1) {
            return 1;
        }
        Map<Integer, Integer> left = new HashMap<>();
        Map<Integer, Integer> right = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();
        int size = nums.length;
        int element;
        for (int i = 0; i < size; i++) {
            element = nums[i];
            if(right.containsKey(element)){
                right.put(element, i);
                count.put(element, count.get(element) + 1);

            }else {
                left.put(element, i);
                right.put(element, i);
                count.put(element, 1);
            }
        }

        int len = nums.length;
        int max = Collections.max(count.values());
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            Integer x = entry.getKey();
            if (max == entry.getValue()){
                len = Math.min(len, right.get(x) - left.get(x) + 1);
            }
        }
        return len;
    }

    public static int findShortestSubArray3(int[] nums) {

        if (nums.length == 1) {
            return 1;
        }
        Map<Integer, Integer> left = new HashMap<>();
        Map<Integer, Integer> right = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();
        //统计每个元素出现的位置
        int size = nums.length;
        int element = 0;
        int max = 1;
        for (int i = 0; i < size; i++) {
            element = nums[i];
            if(right.containsKey(element)){
                right.put(element, i);
                int elementNum = count.get(element) + 1;
                count.put(element, elementNum);
                if (elementNum > max) {
                    max = elementNum;
                }
            }else {
                left.put(element, i);
                right.put(element, i);
                count.put(element, 1);
            }
        }
        if (max == 1) {
            return 1;
        }
        int len = nums.length;
        int elementMinSize;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            Integer x = entry.getKey();
            if (max == entry.getValue()){
                elementMinSize = right.get(x) - left.get(x) + 1;
                if (len >= elementMinSize) {
                    len = elementMinSize;
                }
            }
        }
        return len;
    }
}
