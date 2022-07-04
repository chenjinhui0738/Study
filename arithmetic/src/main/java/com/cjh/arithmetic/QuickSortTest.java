package com.cjh.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 快排算法
 */
public class QuickSortTest {
    @Test
    public void Test1() {
        // 创建测试数组
        int[] arr = new int[]{19, 97, 17, 9, 17, 1, 8};
        System.out.println("排序前：" + Arrays.toString(arr));// 打印数组
        // 快排
        int len;
        //判断数组是否为空或者个数为1
        if (arr == null || (len = arr.length) == 0 || len == 1) {
            return;
        }
        quickSort(arr, 0, len - 1);
        System.out.println("排序后：" + Arrays.toString(arr));// 打印数组
    }

    /**
     * 快排核心算法，递归实现
     *
     * @param array
     * @param left
     * @param right
     */
    public static void quickSort(int[] array, int left, int right) {
        int i = left, j = right;//0,6
        int target = array[left];//19
        while (i < j) {
            //从右向左查找大于基准数的值
            while (i < j && target <= array[j]) {
                j--;
            }
            if (i < j) {
                int temp = array[j];
                array[j] = array[i];
                array[i] = temp;
            }
            System.out.println("排序中：" + Arrays.toString(array));
            //从左向右查找小于基准数的值
            while (i < j && array[i] <= target) {
                i++;
            }
            if (i < j) {
                int temp = array[j];
                array[j] = array[i];
                array[i] = temp;
            }
            System.out.println("排序中：" + Arrays.toString(array));
        }
        if (i - 1 > left) {
            quickSort(array, left, i - 1); // 递归调用
        }

        if (j + 1 < right) {
            quickSort(array, j + 1, right); // 递归调用
        }

    }
}
