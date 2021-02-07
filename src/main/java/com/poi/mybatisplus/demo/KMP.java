package com.poi.mybatisplus.demo;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/28
 * @Version: 1.0
 */
public class KMP {

    public static void main(String[] args) {
        String source = "AAAABABBCC";
        String dect = "ABAB";
        int i = ViolentMatch(source, dect);
        System.out.println("保利搜索匹配位置:" + i);

        i = KmpSearch(source, dect);
        System.out.println("KMP搜索匹配位置:" + i);

        int[] next = getNext(dect);
        for (int i1 : next) {
            System.out.println(i1);
        }

    }

    public static int ViolentMatch(String soucre, String dest) {
        int sLen = soucre.length();
        int dLen = dest.length();
        char[] sc = soucre.toCharArray();
        char[] dc = dest.toCharArray();

        int i = 0;
        int j = 0;
        while (i < sLen && j < dLen) {
            if (sc[i] == dc[j]) {
                //①如果当前字符匹配成功（即S[i] == P[j]），则i++，j++
                i++;
                j++;
            } else {
                //②如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0
                i = i - j + 1;
                j = 0;
            }
        }
        //匹配成功，返回模式串p在文本串s中的位置，否则返回-1
        if (j == dLen){
            return i - j;
        } else {
            return -1;
        }
    }



    public static int KmpSearch(String soucre, String dest) {
        int sLen = soucre.length();
        int dLen = dest.length();
        char[] sc = soucre.toCharArray();
        char[] dc = dest.toCharArray();
        int[] next = getNext(dest);
        int i = 0;
        int j = 0;
        while (i < sLen && j < dLen)
        {
            //①如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++
            if (j == -1 || sc[i] == dc[j]) {
                i++;
                j++;
            } else {
                //②如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]
                //next[j]即为j所对应的next值
                j = next[j];
            }
        }
        if (j == dLen) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static int[] getNext(String dest){
        char[] dc = dest.toCharArray();
        int[] next = new int[dest.length()];
        next[0] = -1;
        int i = 0;
        int k = -1;
        while (i < dest.length() - 1) {

            if (k == -1 || dc[i] == dc[k]){
                k++;
                i++;
                if (dc[i] != dc[k]){
                    next[i] = k;
                } else {
                    next[i] = next[k];
                }

            } else {
                k = next[k];
            }
        }
        return next;
    }
}
