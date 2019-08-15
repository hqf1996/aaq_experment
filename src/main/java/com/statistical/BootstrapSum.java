package com.statistical;

import com.bean.lineitemBean;
import com.util.Util;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:03 2019/8/14
 * @Modified By: 对TPC产生的lineitem.tbl表的linenumber符合条件的总和进行bootstrap估计
 */
public class BootstrapSum {
    public void CalcuSumBootstrap(int b){
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
            // 对每个bootstrap样本算出每个的sum
            Sum sum = new Sum();
            avgBootstrap.add(sum.evaluate(numList));
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
        System.out.println("置信区间为：" + "[" + avgBootstrap.get(k1-1) + ", " + avgBootstrap.get(k2-1)+"]");
        System.out.println(timeConsume1+"ms");
        System.out.println(timeConsume2+"ms");
    }

    public static void main(String[] args) {
        BootstrapSum bootstrapSum = new BootstrapSum();
        bootstrapSum.CalcuSumBootstrap(100);
    }
}
