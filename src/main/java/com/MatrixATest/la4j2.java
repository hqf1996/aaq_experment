package com.MatrixATest;

import com.util.Util;
import org.la4j.Matrix;
import org.la4j.matrix.SparseMatrix;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:49 2019/12/2
 * @Modified By:
 */
public class la4j2 {
    /**
     * 判断两个矩阵是否相等
     * @param entity
     * @param tmp
     * @return
     */
    public static boolean isEqual(Matrix entity, Matrix tmp) {
        for (int i = 0 ; i < entity.columns() ; ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        double [][]a_entity = new double[1][3];
        double [][]a_work = new double[3][3];
        a_entity[0][0] = 0.1;
        a_entity[0][1] = 0.2;
        a_entity[0][2] = 0.7;
        a_work[0][0] = 0.9;
        a_work[0][1] = 0.075;
        a_work[0][2] = 0.025;
        a_work[1][0] = 0.15;
        a_work[1][1] = 0.8;
        a_work[1][2] = 0.05;
        a_work[2][0] = 0.25;
        a_work[2][1] = 0.25;
        a_work[2][2] = 0.5;

        long s = System.currentTimeMillis();
        SparseMatrix entity = SparseMatrix.from2DArray(a_entity);
        SparseMatrix work = SparseMatrix.from2DArray(a_work);
        SparseMatrix tmp;
        System.out.println("set的时间：" + (System.currentTimeMillis() - s));

        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            tmp = entity.multiply(work).toSparseMatrix();
            if (isEqual(entity, tmp)) {
                break;
            }
//            entity.subtract(tmp);
            entity = tmp;
            i++;
        }
        System.out.println("总共消耗时间为：" + (System.currentTimeMillis()-startTime)+"ms");
        System.out.println(i);

    }
}
