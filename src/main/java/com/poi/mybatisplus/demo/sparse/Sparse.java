package com.poi.mybatisplus.demo.sparse;

/**
 * @Description: 稀疏数组
 * @Author: songkai
 * @Date: 2021/2/3
 * @Version: 1.0
 */
public class Sparse {

    public static void main(String[] args) {
        int row = 11;
        int col = 11;

        //一
        //创建二维数组
        int[][] arr = new int[row][col];
        //二维数组赋值
        arr[1][1] = 1;
        arr[2][2] = 2;
        arr[2][3] = 3;
        arr[4][4] = 3;
        arr[5][5] = 3;
        arr[6][6] = 3;
        arr[7][7] = 3;
        arr[8][8] = 3;
        arr[9][9] = 3;
        //打印数组
        for (int[] rows : arr) {
            for (int i : rows) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
        

        //二
        //统计非零值个数
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[j][i] != 0){
                    sum++;
                }
            }
        }

        //创建新数组
        int[][] arr2 = new int[sum + 1][3];
        arr2[0][0] = row;
        arr2[0][1] = col;
        //压缩数组
        int count = 1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0){
                    arr2[count][0] = i;
                    arr2[count][1] = j;
                    arr2[count][2] = arr[i][j];
                    count++;
                }
            }
        }
        //打印数组
        for (int[] rows : arr2) {
            for (int i : rows) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
        //三
        //恢复数组
        //根据压缩数组恢复创建数组
        int[][] arr3 = new int[arr2[0][1]][arr2[0][1]];
        //恢复数组充填值
        for (int i = 1; i < arr2.length; i++) {
            arr3[arr2[i][0]][arr2[i][1]] = arr2[i][2];
        }
        //打印数组
        for (int[] rows : arr3) {
            for (int i : rows) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
        //


    }
}
