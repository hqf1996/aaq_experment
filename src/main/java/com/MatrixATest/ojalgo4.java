package com.MatrixATest;

import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.SparseStore;
import org.ojalgo.type.context.NumberContext;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 15:05 2019/12/2
 * @Modified By:
 */
public class ojalgo4 {
    public static void main(String[] args) {
        final int dim = 3;
        SparseStore<Double> entity = SparseStore.PRIMITIVE.make(1, dim);
        SparseStore<Double> work = SparseStore.PRIMITIVE.make(dim, dim);
        SparseStore<Double> tmp = SparseStore.PRIMITIVE.make(1, dim);

        entity.set(0,0,0.1);
        entity.set(0,1,0.2);
        entity.set(0,2,0.7);
        work.set(0,0,0.9);
        work.set(0,1,0.075);
        work.set(0,2,0.025);
        work.set(1,0,0.15);
        work.set(1,1,0.8);
        work.set(1,2,0.05);
        work.set(2,0,0.25);
        work.set(2,1,0.25);
        work.set(2,2,0.5);

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
//            entity = tmp;
            tmp.supplyTo(entity);
            i++;
            System.out.println(i);
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
