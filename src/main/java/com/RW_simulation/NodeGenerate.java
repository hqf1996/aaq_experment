package com.RW_simulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class NodeGenerate {

	int nodeNumber;		//图中的节点数量
	int accNodeNumber;	//图中正确节点的数量
	double[] pNode;		//存放每个节点的稳态概率（模拟生成，保证和=1）
	int[] vNode;			//存放每个节点的属性值（count对应0，1）
	
	double pSum = 1;		//所有节点之和=1
	double pBound;		//每个节点稳态概率的上限
	
	//用户给出相关参数
	public NodeGenerate(int nodeNumber, int accNodeNumber, double pBound){
		this.nodeNumber = nodeNumber;
		this.accNodeNumber = accNodeNumber;
		this.pBound = pBound;
		
		pNode = new double[this.nodeNumber];
		vNode = new int[this.nodeNumber];
	}
	
	//模拟生成节点的稳态分布
	public void distributionGenerate(){
		double pRand = 0.0D;		//临时存放随机产生的概率值
		double pSum_tmp = pSum;	//存放剩余pSum
		for(int i=1;i<=nodeNumber;i++){
			if(i==nodeNumber){
				pNode[i-1] =  pSum_tmp;
				break;
			}
			while(pRand==0.0D){
				pRand = new Random().nextDouble()*pBound;	//[0,pBound)
				//System.out.println(pRand);
			}
			pNode[i-1] = pRand;
			pBound = (pSum_tmp-pRand)/(nodeNumber-i);
			pSum_tmp = pSum_tmp-pRand;
			pRand = 0.0D;
		}
	}
	
	//生成正确节点：1. 随机选择
	public void accNodeGenerateRand(){
		int[] pos = new int[accNodeNumber];		//随机选择的位置
		HashSet hm = new HashSet();
		for(int i=0;i<accNodeNumber;i++){
			do{
				pos[i] = new Random().nextInt(pNode.length);
			}while(!hm.add(pos[i]));
			vNode[pos[i]] = 1;
		}
	}
	
	//生成正确节点：2. 按概率小的赋值为1
	public void accNodeGenerateLow(){
		Arrays.sort(pNode);
		for(int i=0;i<accNodeNumber;i++){
			vNode[i] = 1;
		}
	}
	
	//生成正确节点：2. 按概率大的赋值为1
	public void accNodeGenerateHigh(){
		Arrays.sort(pNode);
		for(int i=vNode.length-1;i>=vNode.length-accNodeNumber;i--){
			vNode[i] = 1;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
