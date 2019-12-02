package com.MatrixATest;

import com.util.Util;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.SparseStore;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 14:48 2019/12/2
 * @Modified By:
 */
public class ojalgo3 {
    public static void main(String[] args) {

        final int dim = 16094;
        SparseStore<Double> entity = SparseStore.PRIMITIVE.make(1, dim);
        SparseStore<Double> work = SparseStore.PRIMITIVE.make(dim, dim);
        SparseStore<Double> tmp = SparseStore.PRIMITIVE.make(1, dim);

        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        long s = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work.set(x, y, vv);
        }
        entity.set(0, 13520, 1.0);
        System.out.println("set的时间：" + (System.currentTimeMillis() - s));

        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            entity.multiply(work, tmp);
//            if (entity.equals(tmp, new NumberContext(7, 1))) {
//                break;
//            }
            if (isEqual2(entity, tmp)) {
                break;
            }
            tmp.supplyTo(entity);
            i++;
            break;
//            System.out.println(i);
        }
        System.out.println((System.currentTimeMillis()- startTime) + "ms");
        System.out.println(i);
    }

    public static boolean isEqual2(MatrixStore<Double> entity, MatrixStore<Double> tmp) {
        for (int i = 0 ; i < entity.countColumns(); ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }
}
