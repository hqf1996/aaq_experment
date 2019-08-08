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
 * @Modified By:
 */
public class OnlineAggregationAvg {
    /**
    * @Author: hqf
    * @Date:
    * @Description: 计算所有数据的平均值，获得其准确平均值以及其时间消耗
    */
    public void CalcuAvgAll(){
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**计算真实的平均值*/
        long startTime = System.currentTimeMillis();
        double sum = 0;
        for (lineitemBean each : datelist){
            sum+=each.getLinenumber();
        }
        System.out.println(sum/datelist.size());
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }

    public void CalcuAvgOA(){
        double p = 0.95;     //置信度
        int N = 700;         //样本容量
        double []S = new double[N];//样本集
        double e;                  //置信区间
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
//            System.out.println(each);
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        /**用online aggregation估计平均值*/
        long startTime2 = System.currentTimeMillis();
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
        System.out.println("平均值为" + avg);
        System.out.println(e);
        long endTime2 = System.currentTimeMillis();
        System.out.println(endTime2-startTime2);
    }

    public static void main(String[] args) {
        OnlineAggregationAvg onlineAggregationAvg = new OnlineAggregationAvg();
        onlineAggregationAvg.CalcuAvgAll();
        onlineAggregationAvg.CalcuAvgOA();
    }
}
