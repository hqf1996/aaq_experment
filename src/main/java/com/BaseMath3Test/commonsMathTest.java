package com.BaseMath3Test;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;


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
        /*最小值*/
        Min min = new Min();
        /*最大值*/
        Max max = new Max();
        /*平均值*/
        Mean mean = new Mean();
        /*乘积*/
        Product product = new Product();
        /*和*/
        Sum sum = new Sum();
        /*标准方差*/
        Variance variance1 = new Variance(false);
        /*样本方差*/
        Variance variance2 = new Variance();
        /*平方和*/
        SumOfSquares sumOfSquares = new SumOfSquares();
        /*标准差*/
        StandardDeviation StandardDeviation =new StandardDeviation();
//        System.out.println(max.evaluate(values));
//        System.out.println(min.evaluate(values));
//        System.out.println(mean.evaluate(values));
//        System.out.println(product.evaluate(values));
//        System.out.println(sum.evaluate(values));
        System.out.println(variance1.evaluate(values));
        System.out.println(variance2.evaluate(values));
//        System.out.println(sumOfSquares.evaluate(values));
//        System.out.println(StandardDeviation.evaluate(values));
        // 正态分布
//        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
//        /**计算概率 P(X <= x)*/
//        System.out.println(normalDistribution.cumulativeProbability(1.65));
//        /**计算分位数*/
//        System.out.println(normalDistribution.inverseCumulativeProbability(0.95));

    }
}
