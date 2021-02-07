package com.poi.mybatisplus.demo.licode.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/16
 * @Version: 1.0
 */
public class HIndex {
    public static void main(String[] args) {
        int[] nums = {3,0,6,1,5};
        System.out.println( nums + "-正确值:3-" +hIndex(nums));
        int[] nums2 = {100};
        System.out.println( nums2 + "-正确值:1-" +hIndex(nums2));
    }

    public static int hIndex(int[] citations) {
        int len = citations.length;
        if (len == 0){
            return 0;
        }

        //被引用最多的次数
        int maxCitation = citations[0];
        //被引用最少的次数
        int minCitation = citations[0];
        //共计被引用的次数
        int citationCount = 0;
        //被引用的
        /*int citationFrequency = 0;*/

        Map<Integer, Integer> citationMap = new HashMap<>();

        for (int i = 0; i < len; i++){
            int citation = citations[i];
            citationCount += citation;
            if(citationMap.containsKey(citation)){
                citationMap.put(citation, citationMap.get(citation) + 1);
            } else {
                citationMap.put(citation, 1);
            }

            if(citation > maxCitation){
                maxCitation = citation;
            } else if(citation < minCitation) {
                minCitation = citation;
            }
        }

        //当前引用和
        int currentCitationCount = 0;
        for (int i = maxCitation; i >= 0; i--){
            if(citationMap.containsKey(i)){
                currentCitationCount += citationMap.get(i);
            }
            if (currentCitationCount >= i) {
                return i;
            }
        }
        return 0;
    }
}
