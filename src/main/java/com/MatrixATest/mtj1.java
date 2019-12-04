package com.MatrixATest;

import no.uib.cipr.matrix.sparse.*;
/**
 * @Author: hqf
 * @description:
 * @Data: Create in 10:42 2019/12/4
 * @Modified By:
 */
public class mtj1 {
    public static void main(String[] args) {
        LinkedSparseMatrix entity = new LinkedSparseMatrix(1, 3);
        LinkedSparseMatrix work = new LinkedSparseMatrix(3, 3);
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
        LinkedSparseMatrix tmp = new LinkedSparseMatrix(1, 3);
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            entity.mult(work, tmp);
            if (isEqual2(entity, tmp)) {
                break;
            }
            entity.set(tmp);
            i++;
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
