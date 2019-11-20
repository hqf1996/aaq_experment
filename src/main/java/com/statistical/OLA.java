package com.statistical;

import com.query.EntityStatistical;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.List;
import java.util.Map;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 18:55 2019/9/9
 * @Modified By: 对样本进行OLA的估计
 */
public class OLA {
    /**
     * 样本的COUNT估计
     * @param n 样本容量
     * @param p_in 置信度
     * @param sumN_in 总体样本量
     * @param datelist 样本集
     * @param pssLine pss阈值
     */
    public void CalcuCountOLA(int n, double p_in, int sumN_in, List<EntityStatistical> datelist, double pssLine,
                              Map<Integer, Double> map, boolean isWeight) {
        double p = p_in;     //置信度
        int N = n;         //样本容量
        double[] S = new double[N];//样本集
        double[] S_plus = new double[N];//样本集plus
        double e;                  //置信区间
        int sumN = sumN_in;         //总体样本量
        double WeightSum = 0;           //权重之和
        long startTime2 = System.currentTimeMillis();

        int rightCount = 0;
        if (isWeight){
            // 加了权重这么算
            for (int i = 0; i < N; ++i) {
                double weight = map.get(datelist.get(i).getId());
                WeightSum+=weight;
                double pss = datelist.get(i).getPss();
                if (pss >= pssLine) {
                    S[i] = 1*weight;
                    S_plus[i] = 1 * sumN;
                    rightCount++;
                } else {
                    S[i] = 0;
                    S_plus[i] = 0;
                }
            }
        } else {
            // 不加权重这么算
            for (int i = 0; i < N; ++i) {
                double pss = datelist.get(i).getPss();
                if (pss >= pssLine) {
                    S[i] = 1;
                    S_plus[i] = 1 * sumN;
                    rightCount++;
                } else {
                    S[i] = 0;
                    S_plus[i] = 0;
                }
            }
        }

        Sum sum = new Sum();
        System.out.println("sumN=" + sumN);
        System.out.println("N="+N);
        System.out.println(sum.evaluate(S));
        double SumEvaluate;
        if(isWeight){
            System.out.println("WeightSum" + WeightSum);
            SumEvaluate = (1.0*sumN / N) * (N*1.0*sum.evaluate(S)/WeightSum);
        } else {
            SumEvaluate = (1.0*sumN / N) * (sum.evaluate(S));
        }

        Mean mean = new Mean();
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        Variance variance = new Variance();
//        double avg = mean.evaluate(S); //样本均值
        double Zp = normalDistribution.inverseCumulativeProbability((p + 1) / 2.0);//标准正态分布的分位点
        double T = variance.evaluate(S_plus);//样本方差
        e = Math.sqrt(Zp * Zp * T / (N));

//        System.out.println(e);
        System.out.println("样本中正确的结果有：" + rightCount + "个");
        System.out.println("总体的量为" + sumN);
        System.out.println("Sum的估计值为：" + SumEvaluate);
        System.out.println("当前样本容量为：" + N);
        System.out.println("置信度：" + p * 100 + "%");
        System.out.println("置信区间：" + "[" + SumEvaluate + "-" + e + ", " + SumEvaluate + "+" + e + "]");
        long endTime2 = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime2 - startTime2) + "ms");
    }

    /**
     * s-t估计
     * @param n
     * @param p_in
     * @param sumN_in
     * @param datelist
     * @param pssLine
     * @param isWeight
     */
    public void CalcuCountOLA2(int n, double p_in, int sumN_in, List<EntityStatistical> datelist, double pssLine, boolean isWeight, double allTypePi) {
        double p = p_in;     //置信度
        int N = n;         //样本容量
        double[] S = new double[N];//样本集
        double[] S_plus = new double[N];//样本集plus
        double e;                  //置信区间
        int sumN = sumN_in;         //总体样本量
        double WeightSum = 0;           //权重之和
        long startTime2 = System.currentTimeMillis();

        int rightCount = 0;
        if (isWeight){
//            // 加了权重这么算
//            for (int i = 0; i < N; ++i) {
//                double weight = map.get(datelist.get(i).getId());
//                WeightSum+=weight;
//                double pss = datelist.get(i).getPss();
//                if (pss >= pssLine) {
//                    S[i] = 1*weight;
//                    S_plus[i] = 1 * sumN;
//                    rightCount++;
//                } else {
//                    S[i] = 0;
//                    S_plus[i] = 0;
//                }
//            }
        } else {
            // 不加权重这么算
            for (int i = 0; i < N; ++i) {
                double pss = datelist.get(i).getPss();
                if (pss >= pssLine) {
                    S[i] = (1.0/datelist.get(i).getVisitedProbability()) * allTypePi;
                    S_plus[i] = (1.0/datelist.get(i).getVisitedProbability()) * allTypePi;
                    rightCount++;
                } else {
                    S[i] = 0;
                    S_plus[i] = 0;
                }
            }
        }

        Sum sum = new Sum();
        System.out.println("sumN=" + sumN);
        System.out.println("N="+N);
        System.out.println(sum.evaluate(S));
        double SumEvaluate = 0;
        if(isWeight){

        } else {
            SumEvaluate = (1.0*sum.evaluate(S))/N;
        }

        Mean mean = new Mean();
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        Variance variance = new Variance();
//        double avg = mean.evaluate(S); //样本均值
        double Zp = normalDistribution.inverseCumulativeProbability((p + 1) / 2.0);//标准正态分布的分位点
        double T = variance.evaluate(S_plus);//样本方差
        e = Math.sqrt(Zp * Zp * T / (N));

//        System.out.println(e);
        System.out.println("样本中正确的结果有：" + rightCount + "个");
        System.out.println("总体的量为" + sumN);
        System.out.println("Sum的估计值为：" + SumEvaluate);
        System.out.println("当前样本容量为：" + N);
        System.out.println("置信度：" + p * 100 + "%");
        System.out.println("置信区间：" + "[" + SumEvaluate + "-" + e + ", " + SumEvaluate + "+" + e + "]");
        long endTime2 = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime2 - startTime2) + "ms");
    }
}
