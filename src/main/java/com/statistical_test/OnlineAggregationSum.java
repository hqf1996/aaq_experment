package com.statistical_test;

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
 * @Data: Create in 10:05 2019/8/15
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber的Sum值进行online aggregation估计
 */
public class OnlineAggregationSum {
    public double CalcuSumAll(){
        long startTime = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**计算真实的sum*/
        double sum = 0;
        for (lineitemBean each : datelist){
            sum+=each.getLinenumber();
        }
        System.out.println("真实SUM：" + sum);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime-startTime ) + "ms");
        return sum;
    }

    public void CalcuSumOA(int n){
        double p = 0.95;     //置信度
        int N = n;         //样本容量
        double []S = new double[N];//样本集
        double []S_plus = new double[N];//样本集_copy
        double e;                  //置信区间
        int sumN = 600000;         //总体样本量
        long startTime2 = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsoluteByLine("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl", N);
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**用online aggregation估计Sum*/
        for (int i = 0 ; i < N ; ++i){
            S[i] = datelist.get(i).getLinenumber();
            S_plus[i] = datelist.get(i).getLinenumber() * sumN;
        }

        Sum sum = new Sum();
        double SumEvaluate = (sumN/N)*sum.evaluate(S);

        Mean mean = new Mean();
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        Variance variance = new Variance();
//        double avg = mean.evaluate(S_plus); //样本均值
        double Zp = normalDistribution.inverseCumulativeProbability((p + 1) / 2.0);//标准正态分布的分位点
        double T = variance.evaluate(S_plus);//样本方差
        e = Math.sqrt(Zp*Zp*T/(N));

//        System.out.println(e);
        System.out.println("Sum的估计值为：" + SumEvaluate);
        System.out.println("当前样本容量为：" + N);
        System.out.println("置信度：" + p*100 + "%");
        System.out.println("置信区间：" + "[" + SumEvaluate + "-" + e + ", " + SumEvaluate + "+" + e + "]");
        long endTime2 = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime2-startTime2) + "ms");
        double true_sum = this.CalcuSumAll();
        System.out.println("误差率：" + e/true_sum);
    }

    public static void main(String[] args) {
        OnlineAggregationSum onlineAggregationSum = new OnlineAggregationSum();
        onlineAggregationSum.CalcuSumAll();
        onlineAggregationSum.CalcuSumOA(10000);
    }
}
