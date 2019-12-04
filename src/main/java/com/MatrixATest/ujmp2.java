package com.MatrixATest;

import com.util.Util;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:58 2019/12/3
 * @Modified By:
 */
public class ujmp2 {
    public static boolean isEqual2(Matrix entity, Matrix tmp) {
        for (int i = 0 ; i < entity.getColumnCount(); ++i) {
            if (Math.abs(entity.getAsDouble(0, i)-tmp.getAsDouble(0, i)) > 0.0001) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SparseMatrix work = SparseMatrix.Factory.zeros(20139, 20139);
        Matrix entity = Matrix.Factory.zeros(1, 20139);
        List<String> Transmit = Util.readFileAbsolute("D:\\dbpedia_all_graph\\randomWalkTest\\trans.txt");
        entity.setAsDouble(1.0, 0, 16452);

        long s = System.currentTimeMillis();
        for (String each : Transmit) {
            String[] split = each.split("\t");
            int x = Integer.valueOf(split[0]);
            int y = Integer.valueOf(split[1]);
            double vv = Double.valueOf(split[2]);
            work.setAsDouble(vv, x, y);
        }
        System.out.println("set的时间：" + (System.currentTimeMillis() - s));
        Matrix tmp;
        long startTime = System.currentTimeMillis();
//        Matrix mtimes1 = entity.mtimes(Calculation.Ret.LINK, false, work);

        System.out.println(entity.getAsDouble(0,0));
        entity = entity.mtimes(Calculation.Ret.LINK, false, work);
        System.out.println(entity.getAsDouble(0,0));

        entity = entity.mtimes(Calculation.Ret.LINK, false, work);
        System.out.println(entity.getAsDouble(0,0));

//        int i = 0;
//        while (true) {
//            tmp = entity.mtimes(Calculation.Ret.LINK, false, work);
////            if (isEqual2(entity, tmp)) {
////                break;
////            }
//            if (i == 770) {
//                break;
//            }
//            entity = tmp.clone();
//            i++;
//            System.out.println(i);
////            break;
//        }

        System.out.println((System.currentTimeMillis()- startTime) + "ms");
//        System.out.println(entity);

    }
}
