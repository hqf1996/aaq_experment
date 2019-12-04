package com.MatrixATest;

import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.util.MathUtil;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 18:18 2019/12/3
 * @Modified By:
 */
public class ujmp3 {
    public static void main(String[] args) {
        // create a very large sparse matrix
        SparseMatrix m1 = SparseMatrix.Factory.zeros(1000000, 500000);

        // set some values
        m1.setAsDouble(MathUtil.nextGaussian(), 0, 0);
        m1.setAsDouble(MathUtil.nextGaussian(), 1, 1);
        for (int i = 0; i < 10000; i++) {
            m1.setAsDouble(MathUtil.nextGaussian(), MathUtil.nextInteger(0, 1000), MathUtil.nextInteger(0, 1000));
        }


         // create another matrix
        SparseMatrix m2 = SparseMatrix.Factory.zeros(3000000, 500000);

        // set some values
        m2.setAsDouble(MathUtil.nextGaussian(), 0, 0);
        m2.setAsDouble(MathUtil.nextGaussian(), 1, 1);
        for (int i = 0; i < 10000; i++) {
            m2.setAsDouble(MathUtil.nextGaussian(), MathUtil.nextInteger(0, 1000), MathUtil.nextInteger(0, 1000));
        }

        long startTime = System.currentTimeMillis();
        // do some operations
        Matrix m3 = m1.mtimes(m2.transpose());
        System.out.println(m3);
        System.out.println(System.currentTimeMillis()-startTime);

    }
}
