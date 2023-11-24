package com.gy.algorithm.basic.array;

import com.gy.algorithm.basic.ArrayUtils;

public class LC48RotateImage {

    public static void main(String[] args) {

        int[][] dimArray = ArrayUtils.create2DimArrayWithDefaultValue();
        LC48RotateImage lc48RotateImage = new LC48RotateImage();
        lc48RotateImage.rotate(dimArray);
        ArrayUtils.show2DimArray(dimArray);
    }

    /**
     * 考虑不借助辅助矩阵，通过在原矩阵中直接「原地修改」，实现空间复杂度 O(1) 的解法。
     * 以位于矩阵四个角点的元素为例，设矩阵左上角元素 A 、右上角元素 B 、右下角元素 C 、左下角元素 D 。
     * 矩阵旋转 90º 后，相当于依次先后执行 D→A , C→D, B→C A→B 修改元素，即如下「首尾相接」的元素旋转操作：
     * <p>
     * A←D←C←B←A
     * 如上图所示，由于第 1 步 D→A 已经将 A 覆盖（导致A丢失），此丢失导致最后第 4 步 A→B 无法赋值。
     * 为解决此问题，考虑借助一个「辅助变量 tmp 」预先存储 A ，此时的旋转操作变为：
     * <p>
     * 暂存 tmp=A←D←C←B←tmp
     * <p>
     * 如上图所示，一轮可以完成矩阵 4 个元素的旋转。因而，只要分别以矩阵左上角 1/4 的各元素为起始点执行以上旋转操作，即可完整实现矩阵旋转。
     * 具体来看
     * 当矩阵大小 n 为偶数时，取前 n/2行, n/2列 为起点
     * 当矩阵大小 n 为奇数时，取前 2/n行, n+1/2列 为起点
     *
     * 令 Amatrix[i][j]=A，根据文章开头的元素旋转公式，可推导得适用于任意起始点的元素旋转操作：
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;
            }
        }
    }
}
