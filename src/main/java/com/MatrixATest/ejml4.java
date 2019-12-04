package com.MatrixATest;

import org.ejml.data.DMatrix2x2;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.Matrix;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 12:56 2019/12/3
 * @Modified By:
 */
public class ejml4 {
    public static void main(String[] args) {
        double [][]a = new double[2][3];
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[1][0] = 4;
        a[1][1] = 5;
        a[1][2] = 6;
        DMatrixSparseCSC work = new DMatrixSparseCSC(2,3,6);
        DMatrixRMaj test = new DMatrixRMaj(a);
        work.set(test);
        System.out.println(test);
    }
}
