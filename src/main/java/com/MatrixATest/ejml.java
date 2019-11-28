//package com.MatrixATest;
//
//import org.ejml.data.DenseMatrix64F;
//import org.ejml.ops.CommonOps;
//
///**
// * @Author: hqf
// * @description:
// * @Data: Create in 20:28 2019/10/29
// * @Modified By:
// */
//public class ejml {
//    public static int D = 3;
//    public static void main(String[] args) {
//        DenseMatrix64F L = new DenseMatrix64F(3, 3); //初始化一个矩阵，并进行下面的赋值
//        L.set(0, 0, 4.0);
//        L.set(0, 1, 13.0);
//        L.set(0, 2, -16.0);
//        L.set(1, 0, 12.0);
//        L.set(1, 1, 37.0);
//        L.set(1, 2, -43.0);
//        L.set(2, 0, -16.0);
//        L.set(2, 1, -43.0);
//        L.set(2, 2, 98.0);
//        System.out.println("data为:");
//        System.out.println(L);
//
////        DenseMatrix64F[] dataVectors = new DenseMatrix64F[D];  //切分矩阵
////        CommonOps.rowsToVector(L, dataVectors);
////        DenseMatrix64F mu_0  = getSampleMean(dataVectors);  //获取均值
////        System.out.println("数据的均值为:"+mu_0);
////
////        DenseMatrix64F sigma_0 = CommonOps.identity(D); //设置单位阵
////        System.out.println("单位阵为:"+sigma_0);
////
////        CommonOps.scale(3*D, sigma_0); //扩大矩阵
////        System.out.println("扩大后的矩阵为sigma_0:"+sigma_0);
////        for (int i = 0; i < dataVectors.length; i++) {
////            System.out.println("====================");
////            System.out.println(dataVectors[i]);
////        }
////
////        DenseMatrix64F CholSigma0 = new DenseMatrix64F(D,D);
////        CommonOps.addEquals(CholSigma0, sigma_0); //复制矩阵
////        System.out.println("复制后的矩阵为CholSigma0:"+CholSigma0);
////        System.out.println();
////
////        DenseMatrix64F mult_element = new DenseMatrix64F(D,D); //矩阵对应元素相乘
////        CommonOps.elementMult(L, L, mult_element);
////        System.out.println("两个矩阵对应元素相乘的结果为:"+mult_element);
////
////        DenseMatrix64F mult = new DenseMatrix64F(D,D);  //矩阵乘法
////        CommonOps.mult(L, sigma_0, mult);
////        System.out.println("两个矩阵相乘的结果为:"+mult);
////
////        DenseMatrix64F add = new DenseMatrix64F(D,D);  //矩阵对应元素相加
////        CommonOps.add(L, L, add);
////        System.out.println("两个矩阵相加的值为:"+add);
////
////        DenseMatrix64F sub = new DenseMatrix64F(D,D);  //矩阵减法
////        CommonOps.sub(L, sigma_0, sub);;
////        System.out.println("两个矩阵相减的值为:"+sub);
////
////
////        DenseMatrix64F trans = new DenseMatrix64F(D,D);  //矩阵转置
////        CommonOps.transpose(L, trans);
////        System.out.println("转置后的矩阵为:"+trans);
////
////        DenseMatrix64F elementdiv = new DenseMatrix64F(D,D);  //矩阵对应元素相除
////        CommonOps.elementDiv(sigma_0, L, elementdiv);
////        System.out.println("矩阵对应元素相除的结果为:"+elementdiv);
////
////        DenseMatrix64F inverse= new DenseMatrix64F(D,D);  ////矩阵的逆
////        CommonOps.invert(L, inverse);
////        System.out.println("矩阵的逆为:"+inverse);
////    }
////    /**
////     * mean of the data
////     * @param data
////     * @return
////     */
////    public static DenseMatrix64F getSampleMean(DenseMatrix64F[] data)
////    {
////        DenseMatrix64F mean = new DenseMatrix64F(D, 1);//initialized to 0
////        for(DenseMatrix64F vec:data)
////            CommonOps.addEquals(mean, vec);  //获取每一行数据的均值
////
////        CommonOps.divide(data.length, mean);
////
////        return mean;
////    }
//    }
//}
