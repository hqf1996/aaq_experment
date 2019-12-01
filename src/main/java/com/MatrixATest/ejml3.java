package com.MatrixATest;

import com.util.Util;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.sparse.csc.CommonOps_DSCC;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 22:48 2019/12/1
 * @Modified By:
 */
public class ejml3 {
    public static void main(String[] args) {
        DMatrixSparseCSC entity = new DMatrixSparseCSC(1,16094,16094);
        DMatrixSparseCSC work1 = new DMatrixSparseCSC(16094,16094,76468);
        DMatrixSparseCSC work2 = new DMatrixSparseCSC(16094,16094,76468);
        DMatrixSparseCSC tmp = new DMatrixSparseCSC(16094,16094,76468);
        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        entity.set(0, 13520, 1.0);
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work1.set(x, y, vv);
            work2.set(x, y, vv);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0 ; i < 3 ; ++i) {
            CommonOps_DSCC.mult(entity, work2, tmp);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
