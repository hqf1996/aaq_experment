package com.MatrixATest;

import com.util.Util;
import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 10:55 2019/12/4
 * @Modified By:
 */
public class mtj2 {
    public static void main(String[] args) {
        LinkedSparseMatrix entity = new LinkedSparseMatrix(1, 20139);
        LinkedSparseMatrix work = new LinkedSparseMatrix(20139, 20139);
        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        entity.set(0, 16452, 1.0);
        long s = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work.set(x, y, vv);
        }
        System.out.println("set的时间：" + (System.currentTimeMillis() - s));

        LinkedSparseMatrix tmp = new LinkedSparseMatrix(1, 20139);
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            entity.mult(work, tmp);
            if (isEqual2(entity, tmp)) {
                break;
            }
            entity.set(tmp);
            i++;
            System.out.println(i);
        }
        System.out.println((System.currentTimeMillis()- startTime) + "ms");
        System.out.println(i);
    }

    public static boolean isEqual2(LinkedSparseMatrix entity, LinkedSparseMatrix tmp) {
        for (int i = 0 ; i < entity.numColumns(); ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }
}
