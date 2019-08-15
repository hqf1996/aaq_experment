package com.statistical;

import com.bean.lineitemBean;
import com.util.Util;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 11:16 2019/8/8
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber的平均值进行online aggregation估计
 */
public class OnlineAggregationAvg {
    /**
    * @Author: hqf
    * @Date:
    * @Description: 计算所有数据的平均值，获得其准确平均值以及其时间消耗
    */
    public void CalcuAvgAll(){
        long startTime = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**计算真实的平均值*/
        double sum = 0;
        for (lineitemBean each : datelist){
            sum+=each.getLinenumber();
        }
        System.out.println("真实AVG：" + sum/datelist.size());
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime-startTime ) + "ms");
    }

    public void CalcuAvgOA(int n){
        double p = 0.95;     //置信度
        int N = n;         //样本容量
        double []S = new double[N];//样本集
        double e;                  //置信区间
        long startTime2 = System.currentTimeMillis();
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsoluteByLine("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl", N);
        for (String each : list){
//            System.out.println(each);
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**用online aggregation估计平均值*/
        for (int i = 0 ; i < N ; ++i){
            S[i] = datelist.get(i).getLinenumber();
        }
        Mean mean = new Mean();
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        Variance variance = new Variance();
        double avg = mean.evaluate(S);
        double Zp = normalDistribution.inverseCumulativeProbability((p + 1) / 2.0);//标准正态分布的分位点
        double T = variance.evaluate(S);//样本方差
        e = Math.sqrt(Zp*Zp*T/N);

//        System.out.println(e);
        System.out.println("估计的AVG：" + avg);
        System.out.println("当前样本容量为：" + N);
        System.out.println("置信度：" + p*100 + "%");
        System.out.println("置信区间：" + "[" + avg + "-" + e + ", " + avg + "+" + e + "]");
        long endTime2 = System.currentTimeMillis();
        System.out.println("耗时：" + String.valueOf(endTime2-startTime2) + "ms");
    }

    public static void main(String[] args) {
        OnlineAggregationAvg onlineAggregationAvg = new OnlineAggregationAvg();
        onlineAggregationAvg.CalcuAvgAll();
        System.out.println("------------------");
        onlineAggregationAvg.CalcuAvgOA(10000);
//        for (int i = 1 ; i < 101 ; ++i){
//            onlineAggregationAvg.CalcuAvgOA(i*100);
//        }
    }
}
