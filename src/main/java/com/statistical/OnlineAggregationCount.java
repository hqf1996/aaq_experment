package com.statistical;

import com.bean.lineitemBean;
import com.util.Util;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 10:23 2019/8/15
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber的Count值进行online aggregation估计。这边这样假设，要统计的是linenumber字段大于3
 *                的个数，思路和统计sum的基本一致，需要修改的地方是只要将满足情况的点v(i)看成是1，不满足情况的则为0
 */
public class OnlineAggregationCount {
    public double CalcuCountAll(){
        long startTime = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**计算真实的count*/
        double count = 0;
        for (lineitemBean each : datelist){
            if (each.getLinenumber()>=3){
                count++;
            }
        }
        System.out.println("真实COUNT：" + count);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime-startTime ) + "ms");
        return count;
    }

    public void CalcuCountOA(int n) {
        double p = 0.95;     //置信度
        int N = n;         //样本容量
        double[] S = new double[N];//样本集
        double[] S_plus = new double[N];//样本集plus
        double e;                  //置信区间
        int sumN = 600000;         //总体样本量
        long startTime2 = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsoluteByLine("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl", N);
        for (String each : list) {
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**用online aggregation估计Count*/
        for (int i = 0; i < N; ++i) {
            int linenumber = datelist.get(i).getLinenumber();
            if (linenumber >= 3) {
                S[i] = 1;
                S_plus[i] = 1 * sumN;
            } else {
                S[i] = 0;
            }

        }

        Sum sum = new Sum();
        double SumEvaluate = (sumN / N) * sum.evaluate(S);

        Mean mean = new Mean();
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        Variance variance = new Variance();
        double avg = mean.evaluate(S); //样本均值
        double Zp = normalDistribution.inverseCumulativeProbability((p + 1) / 2.0);//标准正态分布的分位点
        double T = variance.evaluate(S_plus);//样本方差
        e = Math.sqrt(Zp * Zp * T / (N));

//        System.out.println(e);
        System.out.println("Sum的估计值为：" + SumEvaluate);
        System.out.println("当前样本容量为：" + N);
        System.out.println("置信度：" + p * 100 + "%");
        System.out.println("置信区间：" + "[" + SumEvaluate + "-" + e + ", " + SumEvaluate + "+" + e + "]");
        long endTime2 = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime2 - startTime2) + "ms");
        double count = this.CalcuCountAll();
        System.out.println("误差率：" + e/count);
    }

    public static void main(String[] args) {
        OnlineAggregationCount onlineAggregationCount = new OnlineAggregationCount();
        onlineAggregationCount.CalcuCountAll();
        onlineAggregationCount.CalcuCountOA(10000);
    }
}
