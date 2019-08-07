package com.statistical;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;

import java.net.NoRouteToHostException;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:13 2019/8/7
 * @Modified By:
 */
public class commonsMathTest {
    public static void main(String[] args) {
        double[] values = new double[] { 0.33, 1.33, 0.27333, 0.3, 0.501,
                0.444, 0.44, 0.34496, 0.33, 0.3, 0.292, 0.667 };
        /*��Сֵ*/
        Min min = new Min();
        /*���ֵ*/
        Max max = new Max();
        /*ƽ��ֵ*/
        Mean mean = new Mean();
        /*�˻�*/
        Product product = new Product();
        /*��*/
        Sum sum = new Sum();
        /*����*/
        Variance variance = new Variance();
        /*ƽ����*/
        SumOfSquares sumOfSquares = new SumOfSquares();
        /*��׼��*/
        StandardDeviation StandardDeviation =new StandardDeviation();
//        System.out.println(max.evaluate(values));
//        System.out.println(min.evaluate(values));
//        System.out.println(mean.evaluate(values));
//        System.out.println(product.evaluate(values));
//        System.out.println(sum.evaluate(values));
//        System.out.println(variance.evaluate(values));
//        System.out.println(sumOfSquares.evaluate(values));
//        System.out.println(StandardDeviation.evaluate(values));
        // ��̬�ֲ�
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        /**������� P(X <= x*/
        System.out.println(normalDistribution.cumulativeProbability(1.65));
        /**�����λ��*/
        System.out.println(normalDistribution.inverseCumulativeProbability(0.95));
    }
}
