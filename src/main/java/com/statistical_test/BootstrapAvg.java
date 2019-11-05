package com.statistical_test;

import com.bean.lineitemBean;
import com.util.Util;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 14:11 2019/8/8
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber的平均值进行bootstrap估计
 */
public class BootstrapAvg {
    public void CalcuAvgBootstrap(int b){
        long timeConsume1 = 0;
        long timeConsume2 = 0;
        long startTime = System.currentTimeMillis();
        // 读取文件数据并封装成对象
        List<lineitemBean> datelist = new ArrayList<>();
        List<String> list = Util.readFileAbsolute("F:\\javaproject\\aaq_experment\\result\\lineitem.tbl");
        for (String each : list){
            String[] split = each.split("\\|");
            lineitemBean item = new lineitemBean(split);
            datelist.add(item);
        }
        double p = 0.95;    //置信度
        int N = datelist.size();    //原始数据样本容量
        int B = b;       //bootstrap样本的数量，在bootstrap估计中一般B要大于等于1000
        /*存放抽样得到的数据*/
//        List<double[]> Samples = new ArrayList<>();
        /*存放bootstrap样本均值的数据*/
        List<Double> avgBootstrap = new ArrayList<>();
        // 开始抽样
        Random random = new Random();
        timeConsume1+=System.currentTimeMillis()-startTime;
        for (int i = 0 ; i < B ; ++i){
            startTime = System.currentTimeMillis();
            double[] numList = new double[N];
            for (int j = 0 ; j < N ; ++j){
                double linenumber = datelist.get(random.nextInt(N)).getLinenumber();
                numList[j] = linenumber;
            }
            timeConsume1+=System.currentTimeMillis()-startTime;
            startTime = System.currentTimeMillis();
            // 对每个bootstrap样本算出每个的均值
            Mean mean = new Mean();
            avgBootstrap.add(mean.evaluate(numList));
            System.out.println("完成第" + (i+1) + "个bootstrap样本抽取");
            timeConsume2+=System.currentTimeMillis()-startTime;
        }
        startTime = System.currentTimeMillis();
        // 并从小到大排序
        Collections.sort(avgBootstrap);
        int k1 = (int) (B * (1-p)/2);
        int k2 = (int) (B * (1-(1-p)/2));
        timeConsume2+=System.currentTimeMillis()-startTime;
//        System.out.println(k1);
//        System.out.println(k2);
        long endTime2 = System.currentTimeMillis();
        System.out.println("置信区间为：" + "[" + avgBootstrap.get(k1-1) + ", " + avgBootstrap.get(k2-1)+"]");
        System.out.println(timeConsume1+"ms");
        System.out.println(timeConsume2+"ms");
    }


    public static void main(String[] args) {
        BootstrapAvg bootstrapAvg = new BootstrapAvg();
        long start = System.currentTimeMillis();
        bootstrapAvg.CalcuAvgBootstrap(100);
        System.out.println(System.currentTimeMillis()-start +"ms");

//        for (int i = 1000 ; i < 1001 ; i+=100){
//            bootstrapAvg.CalcuAvgBootstrap(i);
//        }
    }
}
