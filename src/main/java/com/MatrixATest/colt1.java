package com.MatrixATest;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 0:35 2019/12/4
 * @Modified By:
 */
public class colt1 {
    public static void main(String[] args) {
        DoubleMatrix2D entity = new SparseDoubleMatrix2D(1, 3, 3, 0.9, 0.99);
        DoubleMatrix2D work = new SparseDoubleMatrix2D(3, 3, 3, 0.9, 0.99);
        entity.setQuick(0,0,0.1);
        entity.setQuick(0,1,0.2);
        entity.setQuick(0,2,0.7);
        work.setQuick(0,0,0.9);
        work.setQuick(0,1,0.075);
        work.setQuick(0,2,0.025);
        work.setQuick(1,0,0.15);
        work.setQuick(1,1,0.8);
        work.setQuick(1,2,0.05);
        work.setQuick(2,0,0.25);
        work.setQuick(2,1,0.25);
        work.setQuick(2,2,0.5);

        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            DoubleMatrix2D tmp = Algebra.DEFAULT.mult(entity, work);
            if (isEqual2(entity, tmp)) {
                break;
            }
            entity = tmp;
            i++;
        }
        System.out.println((System.currentTimeMillis()- startTime) + "ms");
        System.out.println(i);
    }

    public static boolean isEqual2(DoubleMatrix2D entity, DoubleMatrix2D tmp) {
        for (int i = 0 ; i < entity.columns(); ++i) {
            if (Math.abs(entity.getQuick(0, i)-tmp.getQuick(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }
}
