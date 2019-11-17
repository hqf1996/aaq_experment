package com.RW_simulation;

import java.util.HashSet;
import java.util.Random;

public class RandomSample {

	int sampleNumber;	//样本容量
	int[] f;				//f(x), COUNT:0,1，从vNode取值
	double[] g;			//g(x), 稳态概率，从pNode取值
	
	public RandomSample(int sampleNumber){
		this.sampleNumber = sampleNumber;
		f = new int[this.sampleNumber];
		g = new double[this.sampleNumber];
	}
	
	//随机选择节点作为样本，纯随机
	public void getRandomSamples(int[] vNode, double[] pNode){
		int pos = 0;
		for(int i=0;i<f.length;i++){
			pos = new Random().nextInt(vNode.length);
			f[i] = vNode[pos];
		}
	}
	
	
	//按照节点稳态概率选择sampleNumber个节点作为样本，采用概率分配数量
	public void getSamples(int[] vNode, double[] pNode){
		double accept = 0.0D;
		int totalSample = 0;		//已获得的样本数量
		int vSum = 0;
		while(totalSample!=sampleNumber){
			for(int i=0;i<pNode.length;i++){
				accept = new Random().nextDouble();
				if(accept<=pNode[i]){
					f[totalSample] = vNode[i];
					g[totalSample] = pNode[i];
					System.out.println("i"+i);
					totalSample ++;
					if(totalSample==sampleNumber){
						break;
					}
				}
			}
		}
		/*System.out.println("vSum= "+vSum);
		System.out.println("totalSample= "+totalSample);*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}
}
