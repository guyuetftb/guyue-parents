package com.gy.algorithm.basic;

import java.util.Arrays;

public class ArrayUtils {

    public static int[][] create2DimArrayWithDefaultValue() {
        return new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    }

    public static int[][] create2DimArrayEmpty(int length) {
        return new int[length][length];
    }

    public static void show2DimArray(int[][] dimArray) {
        Arrays.stream(dimArray).forEach(x -> {
            Arrays.stream(x).forEach(System.out::println);
        });
    }
}
