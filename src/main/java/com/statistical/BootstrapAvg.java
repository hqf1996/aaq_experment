package com.statistical;

import com.bean.lineitemBean;
import com.util.Util;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.UniformRandomGenerator;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 14:11 2019/8/8
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber的平均值进行bootstrap估计
 */
public class BootstrapAvg {
    public void CalcuAvgBootstrap(){
        // 读取文件数据并封装成对象
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        double p = 0.9;    //置信度
        int N = datelist.size();    //原始数据样本容量
        int B = 100;       //bootstrap样本的数量，在bootstrap估计中一般B要大于等于1000
        /*存放抽样得到的数据*/
        List<double[]> Samples = new ArrayList<>();
        // 开始抽样
        Random random = new Random();
        for (int i = 0 ; i < B ; ++i){
            double[] numList = new double[N];
            for (int j = 0 ; j < N ; ++j){
                double linenumber = datelist.get(random.nextInt(N)).getLinenumber();
                numList[j] = linenumber;
            }
            Samples.add(numList);
            System.out.println("完成第" + (i+1) + "个bootstrap样本抽取");
        }
        // 对每个bootstrap样本算出每个的均值
        Mean mean = new Mean();
        List<Double> avgBootstrap = new ArrayList<>();
        for (double[] each : Samples){
            avgBootstrap.add(mean.evaluate(each));
        }
        // 并从小到大排序
        Collections.sort(avgBootstrap);
        int k1 = (int) (B * (1-p)/2);
        int k2 = (int) (B * (1-(1-p)/2));
        System.out.println(k1);
        System.out.println(k2);
        System.out.println("置信区间为：" + "(" + avgBootstrap.get(k1+1) + ", " + avgBootstrap.get(k2+1)+")");
    }

    public static void main(String[] args) {
        BootstrapAvg bootstrapAvg = new BootstrapAvg();
        bootstrapAvg.CalcuAvgBootstrap();
    }
}
