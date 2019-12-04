package com.MatrixATest;

import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:28 2019/12/3
 * @Modified By:
 */
public class ujmp1 {
    public static void main(String[] args) {
        SparseMatrix work = SparseMatrix.Factory.zeros(3, 3);
        Matrix entity = Matrix.Factory.zeros(1, 3);
        entity.setAsDouble(0.1, 0,0);
        entity.setAsDouble(0.2, 0,1);
        entity.setAsDouble(0.7, 0,2);
        work.setAsDouble(0.9, 0,0);
        work.setAsDouble(0.075, 0,1);
        work.setAsDouble(0.025, 0,2);
        work.setAsDouble(0.15, 1,0);
        work.setAsDouble(0.8, 1,1);
        work.setAsDouble(0.05, 1,2);
        work.setAsDouble(0.25, 2,0);
        work.setAsDouble(0.25, 2,1);
        work.setAsDouble(0.5, 2,2);
//        System.out.println(entity);
        Matrix tmp;
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
//            tmp = entity.mtimes(Calculation.Ret.LINK, false, work);
            tmp = entity.mtimes(Calculation.Ret.LINK, false, work);
            if (isEqual2(entity, tmp)) {
                break;
            }
//            if (i == 27) {
//                break;
//            }
            entity = tmp.clone();
            i++;
//            System.out.println(i);
        }
        System.out.println((System.currentTimeMillis()- startTime) + "ms");
        System.out.println(i);
    }

    public static boolean isEqual2(Matrix entity, Matrix tmp) {
        for (int i = 0 ; i < entity.getColumnCount(); ++i) {
            if (Math.abs(entity.getAsDouble(0, i)-tmp.getAsDouble(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }
}
