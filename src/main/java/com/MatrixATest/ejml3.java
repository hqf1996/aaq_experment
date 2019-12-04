package com.MatrixATest;

import com.util.Util;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.sparse.csc.CommonOps_DSCC;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 22:48 2019/12/1
 * @Modified By:
 */
public class ejml3 {
    /**
     * 判断两个矩阵是否相等
     * @param entity
     * @param tmp
     * @return
     */
    public static boolean isEqual(DMatrixRMaj entity, DMatrixRMaj tmp) {
        for (int i = 0 ; i < entity.getNumCols() ; ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        DMatrixSparseCSC entity = new DMatrixSparseCSC(1,16094,16094);
//        DMatrixSparseCSC work = new DMatrixSparseCSC(16094,16094,76468);
//        DMatrixSparseCSC tmp = new DMatrixSparseCSC(16094,16094,76468);

//        double[][] a_entity = new double[1][20139];
        double[][] a_work = new double[20139][20139];
//        double [][]a_tmp = new double[16094][16094];
        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
//        a_entity[0][16452] = 1.0;
        long start = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            a_work[x][y] = vv;
//            work.set(x, y, vv);
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
//        DMatrixRMaj entity = new DMatrixRMaj(a_entity);
        DMatrixRMaj work = new DMatrixRMaj(a_work);
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(work.get(4066, 4093));
//        DMatrixRMaj tmp = new DMatrixRMaj(20139, 20139);
////        CommonOps_DDRM.mult(entity, work, tmp);
//
//        long startTime = System.currentTimeMillis();
//        int i = 0;
//        while (true) {
//            CommonOps_DDRM.mult(entity, work, tmp);
//            if (isEqual(entity, tmp)) {
//                break;
//            }
//            entity.set(tmp);
//            i++;
//            System.out.println(i);
//        }
//        System.out.println("总共消耗时间为：" + (System.currentTimeMillis() - startTime) + "ms");
//        System.out.println(i);
    }
}
