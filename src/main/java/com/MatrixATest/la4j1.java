package com.MatrixATest;

import com.util.Util;
import org.la4j.Matrix;
import org.la4j.matrix.SparseMatrix;
import org.la4j.matrix.sparse.CRSMatrix;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:49 2019/12/2
 * @Modified By:
 */
public class la4j1 {
    /**
     * 判断两个矩阵是否相等
     * @param entity
     * @param tmp
     * @return
     */
    public static boolean isEqual(CRSMatrix entity, CRSMatrix tmp) {
        for (int i = 0 ; i < entity.columns() ; ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        double [][]a_entity = new double[1][20139];
        double [][]a_work = new double[20139][20139];

        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        long s = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            a_work[x][y] = vv;
        }
        a_entity[0][16452] = 1.0;
//        SparseMatrix entity = SparseMatrix.from2DArray(a_entity);
//        SparseMatrix work = SparseMatrix.from2DArray(a_work);
//        SparseMatrix tmp;

        CRSMatrix entity = CRSMatrix.from2DArray(a_entity);
        CRSMatrix work = CRSMatrix.from2DArray(a_work);
        CRSMatrix tmp;

        System.out.println("set的时间：" + (System.currentTimeMillis() - s));

        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            tmp = (CRSMatrix) entity.multiply(work);
            if (isEqual(entity, tmp)) {
                break;
            }
            entity = tmp;
            i++;
        }
        System.out.println("总共消耗时间为：" + (System.currentTimeMillis()-startTime)+"ms");
        System.out.println(i);

    }
}
