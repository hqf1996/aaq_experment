package com.MatrixATest;

import com.util.Util;
import org.ejml.data.*;
import org.ejml.sparse.csc.CommonOps_DSCC;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:28 2019/10/30
 * @Modified By:
 */
public class ejml2 {
    public static int ROWS = 100000;
    public static int COLS = 1000;
    public static int XCOLS = 1;


    /**
     * 判断两个矩阵是否相等
     * @param entity
     * @param tmp
     * @return
     */
    public static boolean isEqual(DMatrixSparseCSC entity, DMatrixSparseCSC tmp) {
        for (int i = 0 ; i < entity.getNumCols() ; ++i) {
            if (Math.abs(entity.get(0, i)-tmp.get(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        Random rand = new Random(234);

        // easy to work with sparse format, but hard to do computations with
        // NOTE: It is very important to you set 'initLength' to the actual number of elements in the final array
        //       If you don't it will be forced to thrash memory as it grows its internal data structures.
        //       Failure to heed this advice will make construction of large matrices 4x slower and use 2x more memory

//        DMatrixSparseTriplet entity = new DMatrixSparseTriplet(1,3,3);
//        DMatrixSparseTriplet work = new DMatrixSparseTriplet(3,3,3);


        DMatrixSparseCSC entity = new DMatrixSparseCSC(1,16094,16094);
        DMatrixSparseCSC work = new DMatrixSparseCSC(16094,16094,76468);
        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
//        for (int i = 0 ; i < entity.getNumCols() ; ++i) {
//            entity.set(0, i, 1.0/14610);
//        }
        entity.set(0, 13520, 1.0);
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work.set(x, y, vv);
        }
        DMatrixSparseCSC tmp = new DMatrixSparseCSC(1,16094,16094);
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            CommonOps_DSCC.mult(entity, work, tmp);
            if (isEqual(entity, tmp)) {
                break;
            }
            entity.set(tmp);
//            System.out.println(entity.nz_length);
//            System.out.println(entity.get(0,8075));
//            System.out.println(entity.get(0,10458));
            i++;
        }
        System.out.println("总共消耗时间为：" + (System.currentTimeMillis()-startTime)+"ms");
        System.out.println(i);

//        DMatrixSparseCSC entity = new DMatrixSparseCSC(1,5,5);
//        DMatrixSparseCSC work = new DMatrixSparseCSC(5,5,25);
//        entity.set(0,0,1.0);
//        work.set(0,1,0.4);
//        work.set(0,2,0.6);
//        work.set(1,0,0.3);
//        work.set(1,3,0.7);
//        work.set(2,0,0.8);
//        work.set(2,4,0.2);
//        work.set(3,1,1.0);
//        work.set(4,2,1.0);
//
//        int i =0 ;
//        DMatrixSparseCSC tmp = new DMatrixSparseCSC(1,5,5);
//        while (i < 10) {
//            CommonOps_DSCC.mult(entity, work, tmp);
//            entity.set(tmp);
//            entity.print();
//            i++;
//        }

//        entity.set(0,0,0.1);
//        entity.set(0,1,0.2);
//        entity.set(0,2,0.7);
//        work.set(0,0,0.9);
//        work.set(0,1,0.075);
//        work.set(0,2,0.025);
//        work.set(1,0,0.15);
//        work.set(1,1,0.8);
//        work.set(1,2,0.05);
//        work.set(2,0,0.25);
//        work.set(2,1,0.25);
//        work.set(2,2,0.5);
//        entity.print();
//        DMatrixSparseCSC tmp = new DMatrixSparseCSC(1,3,3);
//        CommonOps_DSCC.mult(entity, work, tmp);
//        tmp.print();
//        entity.set(tmp);
//        CommonOps_DSCC.mult(entity, work, tmp);
//        tmp.print();
        
//        DMatrixSparseCSC tmp = new DMatrixSparseCSC(1,3,3);
//        for (int i = 0 ; i < 30 ; ++i) {
//            CommonOps_DSCC.mult(entity, work, tmp);
//            entity.set(tmp);
//            tmp.print();
//            System.out.println(i);
//        }
//        // convert into a format that's easier to perform math with
//        DMatrixSparseCSC Z = ConvertDMatrixStruct.convert(work,(DMatrixSparseCSC)null);
//
//        // print the matrix to standard out in two different formats
//        Z.print();
//        System.out.println();
//        Z.printNonZero();
//        System.out.println();
//
//        // Create a large matrix that is 5% filled
//        DMatrixSparseCSC A = new DMatrixSparseCSC(ROWS,COLS,(int)(ROWS*COLS*0.05));
//        //          large vector that is 70% filled
//        DMatrixSparseCSC x = RandomMatrices_DSCC.rectangle(COLS,XCOLS,(int)(XCOLS*COLS*0.7),rand);
//
//        System.out.println("Done generating random matrices");
//        // storage for the initial solution
//        DMatrixSparseCSC y = new DMatrixSparseCSC(ROWS,XCOLS,0);
//        DMatrixSparseCSC z = new DMatrixSparseCSC(ROWS,XCOLS,0);
//
//        // To demonstration how to perform sparse math let's multiply:
//        //                  y=A*x
//        // Optional storage is set to null so that it will declare it internally
//        long before = System.currentTimeMillis();
//        IGrowArray workA = new IGrowArray(A.numRows);
//        DGrowArray workB = new DGrowArray(A.numRows);
//        for (int i = 0; i < 100; i++) {
//            CommonOps_DSCC.mult(A,x,y,workA,workB);
//            CommonOps_DSCC.add(1.5,y,0.75,y,z,workA,workB);
//        }
//        long after = System.currentTimeMillis();
//
//        System.out.println("norm = "+ NormOps_DSCC.fastNormF(y)+"  sparse time = "+(after-before)+" ms");
//
//        DMatrixRMaj Ad = ConvertDMatrixStruct.convert(A,(DMatrixRMaj)null);
//        DMatrixRMaj xd = ConvertDMatrixStruct.convert(x,(DMatrixRMaj)null);
//        DMatrixRMaj yd = new DMatrixRMaj(y.numRows,y.numCols);
//        DMatrixRMaj zd = new DMatrixRMaj(y.numRows,y.numCols);
//
//        before = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++) {
//            CommonOps_DDRM.mult(Ad, xd, yd);
//            CommonOps_DDRM.add(1.5,yd,0.75, yd, zd);
//        }
//        after = System.currentTimeMillis();
//        System.out.println("norm = "+ NormOps_DDRM.fastNormF(yd)+"  dense time  = "+(after-before)+" ms");

    }
}
