package com.MatrixATest;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import com.util.Util;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 9:50 2019/12/4
 * @Modified By:
 */
public class colt2 {
    public static void main(String[] args) {
        DoubleMatrix2D entity = new SparseDoubleMatrix2D(1, 20139, 20139, 0.98, 0.99);
        DoubleMatrix2D work = new SparseDoubleMatrix2D(20139, 20139, 50474, 0.98, 0.99);

        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        entity.setQuick(0, 16452, 1.0);
        long s = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work.setQuick(x, y, vv);
        }
        System.out.println("set的时间：" + (System.currentTimeMillis() - s));
        DoubleMatrix2D tmp;
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            tmp = Algebra.DEFAULT.mult(entity, work);
            if (isEqual2(entity, tmp)) {
                break;
            }
            entity = tmp;
            i++;
        }
        System.out.println("总共消耗时间为：" + (System.currentTimeMillis()-startTime)+"ms");
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
