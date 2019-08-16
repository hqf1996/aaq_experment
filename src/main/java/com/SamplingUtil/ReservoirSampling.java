package com.SamplingUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:57 2019/8/15
 * @Modified By: 水塘抽样问题
 */
public class ReservoirSampling {
    private final int N = 100000;  //数据规模 数字从0-99999
    private int[] pool = new int[N];    //所有数据
    private Random random = new Random();

    private int[] simpling(int K){
        int []result = new int[K];
        //pool的初始化
        for (int i = 0 ; i <  N ; ++i){
            pool[i] = i;
        }
        // 先将前K个元素放入数组
        for (int i = 0 ; i < K ; ++i){
            result[i] = pool[i];
        }
        //从第K+1个元素开始抽样
        for (int i = K+1 ; i < N ; ++i){
            int r = random.nextInt(i+1);
            if (r < K){
                result[r] = pool[i];
            }
        }
        System.out.println(Arrays.toString(result));
        return result;
    }

    public static void main(String[] args) {
        ReservoirSampling rs = new ReservoirSampling();
        rs.simpling(10);
    }
}
