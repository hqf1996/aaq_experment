package com.RW_simulation;

public class Estimate {
	
	double estimator = 0.0D;		//近似估计值
	double errorBound = 0.0D;	//误差界
	double confidence = 0.0D;	//置信度
	double mean = 0.0D;			//样本均值
	double variance = 0.0D;		//样本方差
	double[] randVariable;		//f(xi)/g(xi)
	
	public Estimate(double confidence){
		this.confidence = confidence;
	}
	
	//获得估计值，参考s-t方法的估计
	public void getEstimator(int[] f, double[] g){
		randVariable = new double[f.length];
		double sumRV = 0.0D;		//随机变量之和
		for(int i=0;i<f.length;i++){
			randVariable[i] = (double) f[i]/g[i];
			sumRV += randVariable[i];
		}
		estimator = sumRV/(double) f.length;
	}
	
	//获得估计值，OLA估计方法
	public void getEstimatorOLA(int[] f, double[] g, int nodeNumber){
		randVariable = new double[f.length];
		double sumRV = 0.0D;		//随机变量之和
		for(int i=0;i<f.length;i++){
			randVariable[i] = (double) nodeNumber*f[i];
			sumRV += randVariable[i];
		}
		estimator = sumRV/(double) f.length;
	}
	
	//获得样本方差
	public void getVariance(){
		double sumDiffPow = 0.0D;	//随机变量与均值的平方和
		mean = estimator;
		for(int i=0;i<randVariable.length;i++){
			sumDiffPow += Math.pow(Math.abs(randVariable[i]-mean), 2.0D);
		}
		variance = sumDiffPow/randVariable.length;
	}
	
	//获得误差界，中心极限定理
	public void getErrorBound(){
		getVariance();
		double p = (confidence + 1.0D)/2.0D;
		Gaussian gau = new Gaussian();
		double inverseCDF = gau.inverseCDF(p);
		errorBound = inverseCDF*Math.sqrt(variance)/Math.sqrt((double)randVariable.length);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NodeGenerate ng = null;
		RandomSample rs = null;
		double avgEstimator = 0.0D;
		double sumEstimator = 0.0D;
		double avgErrorBound = 0.0D;
		double sumErrorBound = 0.0D;
		
		int group = 10;		//一次实验group组，每组count次
		int count = 100;		//估计的次数，取均值
		//选择一个正确节点的生成策略，运行group组测试，每组count次，每组算个均值
		for(int i=0;i<group;i++){
			for(int j=0;j<count;j++){
				ng = new NodeGenerate(8342,600,0.00012);
//				ng = new NodeGenerate(1000,100,0.001);
				rs = new RandomSample(2000);	//设置不同的样本量
				ng.distributionGenerate();
				
				// 1. 选择一个正确节点的生成方法
//				ng.accNodeGenerateRand();
				ng.accNodeGenerateHigh();
				//ng.accNodeGenerateLow();
				
				// 2. 选择一个样本采集方法：s-t估计则是根据概率取，OLA两个均可
				rs.getSamples(ng.vNode,ng.pNode);
//				rs.getRandomSamples(ng.vNode, ng.pNode);
				
				Estimate est = new Estimate(0.9);
				
				// 2. 选择一个估计方法: s-t用getEstimator OLA用getEstimatorOLA
				est.getEstimator(rs.f, rs.g);
//				est.getEstimatorOLA(rs.f, rs.g, ng.nodeNumber);
				
				est.getErrorBound();
				sumEstimator += est.estimator;
				sumErrorBound += est.errorBound;				
			}	
			System.out.println(sumEstimator/count+" "+sumErrorBound/count);
			avgEstimator = 0.0D;
			sumEstimator = 0.0D;
			avgErrorBound = 0.0D;
			sumErrorBound = 0.0D;
		}
	}
	
}
