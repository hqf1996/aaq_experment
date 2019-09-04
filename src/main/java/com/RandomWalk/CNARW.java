package com.RandomWalk;

import java.util.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 20:51 2019/8/22
 * @Modified By:
 */
public class CNARW extends Graph<NodeCNARW, Double>{

    Graph graph;
    List<String> AllPath = new ArrayList<>();      //所有的路径
    Map<String, Integer> PathBefore = new LinkedHashMap<>();   //在稳态之间对路径的统计
    Map<String, Integer> PathAfter = new LinkedHashMap<>();    //在稳态之后对路径的统计
    double [][]P_value;
    int nodeNum;
    /**
     * 初始化图
     * @return
     */
    private void initGraph(){
        Graph<NodeCNARW, Double> graph = new Graph<>(false);
//        String []nodes_names = {"u" ,"a", "b", "c", "d", "v", "e", "f", "g", "h", "add1", "add2", "add3", "add4", "add5", "add6",
//                "add7", "add8", "add9", "add10", "add11", "add12", "add13", "add14", "add15"};
//        double []nodes_values = {1.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String []nodes_names = {"u" ,"a", "b", "c", "d", "v", "e", "f", "g", "h"};
        double []nodes_values = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        NodeCNARW []nodes = new NodeCNARW[nodes_names.length];
        for (int i = 0 ; i < nodes_names.length ; ++i){
            nodes[i] = new NodeCNARW(nodes_names[i], nodes_values[i]);
        }
//        NodeCNARW [][]edges = {
//                {new NodeCNARW("u", 1.0), new NodeCNARW("a", 0)},
//                {new NodeCNARW("u", 1.0), new NodeCNARW("b", 0)},
//                {new NodeCNARW("u", 1.0), new NodeCNARW("c", 0)},
//                {new NodeCNARW("u", 1.0), new NodeCNARW("d", 0)},
//                {new NodeCNARW("u", 1.0), new NodeCNARW("v", 0)},
//                {new NodeCNARW("a", 0), new NodeCNARW("b", 0)},
//                {new NodeCNARW("b", 0), new NodeCNARW("d", 0)},
//                {new NodeCNARW("b", 0), new NodeCNARW("e", 0)},
//                {new NodeCNARW("d", 0), new NodeCNARW("c", 0)},
//                {new NodeCNARW("c", 0), new NodeCNARW("f", 0)},
//                {new NodeCNARW("c", 0), new NodeCNARW("h", 0)},
//                {new NodeCNARW("v", 0), new NodeCNARW("g", 0)},
//                {new NodeCNARW("v", 0), new NodeCNARW("e", 0)},
//                {new NodeCNARW("v", 0), new NodeCNARW("f", 0)},
//                {new NodeCNARW("e", 0), new NodeCNARW("g", 0)},
//                {new NodeCNARW("g", 0), new NodeCNARW("h", 0)},
//                {new NodeCNARW("add1", 0), new NodeCNARW("a", 0)},
//                {new NodeCNARW("add1", 0), new NodeCNARW("g", 0)},
//                {new NodeCNARW("add1", 0), new NodeCNARW("add2", 0)},
//                {new NodeCNARW("add2", 0), new NodeCNARW("v", 0)},
//                {new NodeCNARW("d", 0), new NodeCNARW("add3", 0)},
//                {new NodeCNARW("add3", 0), new NodeCNARW("h", 0)},
//                {new NodeCNARW("g", 0), new NodeCNARW("add4", 0)},
//                {new NodeCNARW("add4", 0), new NodeCNARW("add5", 0)},
//                {new NodeCNARW("add5", 0), new NodeCNARW("add6", 0)},
//                {new NodeCNARW("h", 0), new NodeCNARW("add6", 0)},
//                {new NodeCNARW("add6", 0), new NodeCNARW("add7", 0)},
//                {new NodeCNARW("add7", 0), new NodeCNARW("add8", 0)},
//                {new NodeCNARW("add6", 0), new NodeCNARW("add9", 0)},
//                {new NodeCNARW("h", 0), new NodeCNARW("add7", 0)},
//                {new NodeCNARW("add4", 0), new NodeCNARW("add9", 0)},
//                {new NodeCNARW("add8", 0), new NodeCNARW("add9", 0)},
//                {new NodeCNARW("h", 0), new NodeCNARW("add10", 0)},
//                {new NodeCNARW("add3", 0), new NodeCNARW("add10", 0)},
//                {new NodeCNARW("add10", 0), new NodeCNARW("add7", 0)},
//                {new NodeCNARW("add10", 0), new NodeCNARW("add11", 0)},
//                {new NodeCNARW("add11", 0), new NodeCNARW("add7", 0)},
//                {new NodeCNARW("add11", 0), new NodeCNARW("add12", 0)},
//                {new NodeCNARW("add12", 0), new NodeCNARW("add9", 0)},
//                {new NodeCNARW("add10", 0), new NodeCNARW("add13", 0)},
//                {new NodeCNARW("add13", 0), new NodeCNARW("add14", 0)},
//                {new NodeCNARW("add5", 0), new NodeCNARW("add15", 0)},
//                {new NodeCNARW("add15", 0), new NodeCNARW("add9", 0)},
//                {new NodeCNARW("add14", 0), new NodeCNARW("add12", 0)},
//                {new NodeCNARW("add14", 0), new NodeCNARW("add8", 0)}
//                };
//        Double[] predicates = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
        NodeCNARW [][]edges = {{new NodeCNARW("u", 1), new NodeCNARW("a", 0)},
                {new NodeCNARW("u", 1), new NodeCNARW("b", 0)},
                {new NodeCNARW("u", 1), new NodeCNARW("c", 0)},
                {new NodeCNARW("u", 1), new NodeCNARW("d", 0)},
                {new NodeCNARW("u", 1), new NodeCNARW("v", 0)},
                {new NodeCNARW("a", 0), new NodeCNARW("b", 0)},
                {new NodeCNARW("b", 0), new NodeCNARW("d", 0)},
                {new NodeCNARW("b", 0), new NodeCNARW("e", 0)},
                {new NodeCNARW("d", 0), new NodeCNARW("c", 0)},
                {new NodeCNARW("c", 0), new NodeCNARW("f", 0)},
                {new NodeCNARW("c", 0), new NodeCNARW("h", 0)},
                {new NodeCNARW("v", 0), new NodeCNARW("g", 0)},
                {new NodeCNARW("v", 0), new NodeCNARW("e", 0)},
                {new NodeCNARW("v", 0), new NodeCNARW("f", 0)},
                {new NodeCNARW("e", 0), new NodeCNARW("g", 0)},
                {new NodeCNARW("g", 0), new NodeCNARW("h", 0)}};
        Double[] predicates = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        graph.createGraph(nodes, edges, predicates);
        this.graph = graph;
        nodeNum = nodes_names.length;
        P_value = new double[nodeNum][nodeNum];
        long startTime = System.currentTimeMillis();
        CalTransitionMatrix(nodeNum);
        System.out.println("计算转移矩阵的时间为：" + String.valueOf(System.currentTimeMillis()-startTime) + "ms");
    }

    /**
     * 计算出转移矩阵
     * @param nodesNum
     */
    private void CalTransitionMatrix(int nodesNum){
        for (int i = 0 ; i < nodesNum ; ++i){
            for (int j = 0 ; j < nodesNum ; ++j){
                P_value[i][j] = this.getP_uvValue(i, j);
            }
        }
    }

    /**
     * 通过直接计算，返回两个点的P值
     * @param start
     * @param end
     * @return
     */
    private double getP_uvValue(int start, int end) {
        Iterator<Graph.ArcNode> iterator = graph.iterator(start);
        List<Integer> nextAdjvex = new ArrayList<>();
        // 获取当前节点的所有临界点的adjvex
        while (iterator.hasNext()) {
            ArcNode arcNode = iterator.next();
            int adjvex = arcNode.adjvex;
            nextAdjvex.add(adjvex);
        }
        VexNode<NodeCNARW> nodeCNARWVexNode1 = graph.GetDataByIndex(start);
        VexNode<NodeCNARW> nodeCNARWVexNode2 = graph.GetDataByIndex(end);
//        System.out.println(nodeCNARWVexNode1.data.node_name + "->" + nodeCNARWVexNode2.data.node_name);
        //如果两个点没有边直接相连，则直接返回0即可
        if (!nextAdjvex.contains(end)){
//            System.out.println(nodeCNARWVexNode1.data.node_name + "->" + nodeCNARWVexNode2.data.node_name + ":" + 0.0);
            return 0.0;
        }
        //计算当前节点所有边的q值和，可以重用，就不用重复计算
        double q_sum = 0.0;
        for (Integer each : nextAdjvex) {
            int[] result = getPublicNodeNumAndDeg(start, each);
            int minDeg = result[1];  //度的最小值
            int C = result[0];  //公共点数
            q_sum += (1 - 1.0 * C / minDeg);
        }
        // 计算quv
        int[] result = getPublicNodeNumAndDeg(start, end);
        int C = result[0];  //公共点数
        int minDeg = result[1];  //度的最小值
        double q_uv = 1 - 1.0 * C / minDeg;
        double p_uv_ba = 1.0 * q_uv / getDegree(start);
        double p_uu_ba = 1 - (1.0 / getDegree(start)) * (q_sum);
        double P_uv = p_uv_ba / (1 - p_uu_ba);
//        System.out.println(nodeCNARWVexNode1.data.node_name + "->" + nodeCNARWVexNode2.data.node_name + ":" + P_uv);
        return P_uv;
    }

    /**
     * 通过先前就计算好的转移矩阵来取得当前两个点之间的P值
     * @param start
     * @param end
     * @return
     */
    private double getP_uvValueByMatrix(int start, int end){
        return P_value[start][end];
    }

    /**
     * 在进行样本的估计时，返回点的权重
     * @param start
     * @return
     */
    private double getWeight(int start) {
        Iterator<Graph.ArcNode> iterator = graph.iterator(start);
        List<Integer> nextAdjvex = new ArrayList<>();
        // 获取当前节点的所有邻接点的adjvex
        while (iterator.hasNext()) {
            ArcNode arcNode = iterator.next();
            int adjvex = arcNode.adjvex;
            nextAdjvex.add(adjvex);
        }
        //计算当前节点所有边的q值和，可以重用，就不用重复计算
        double q_sum = 0.0;
        for (Integer each : nextAdjvex) {
            int[] result = getPublicNodeNumAndDeg(start, each);
            int C = result[0];  //公共点数
            int minDeg = result[1];  //度的最小值
            q_sum += (1 - 1.0 * C / minDeg);
        }
        // 计算r(u)
        double r_u = 1.0 / (1.0 / getDegree(start)) * (q_sum);
        double w_u = r_u/getDegree(start);
        return w_u;
    }

    /**
     * 获得节点的度
     * @param adjvex
     * @return
     */
    private int getDegree(int adjvex){
        int count = 0;
        Iterator<Graph.ArcNode> iterator = graph.iterator(adjvex);
        while (iterator.hasNext()){
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * 拿到cur 和 adjvex两个点的共有点以及两个点度的最小值
     * @param cur
     * @param adjvex
     * @return
     */
    private int[] getPublicNodeNumAndDeg(int cur, int adjvex) {
        Iterator<Graph.ArcNode> iterator = graph.iterator(cur);
        Iterator<Graph.ArcNode> iterator_adjvex = graph.iterator(adjvex);
        List<Integer> curList = new ArrayList<>();
        List<Integer> adjvexList = new ArrayList<>();
        // 当前点遍历的邻居节点
        while (iterator.hasNext()){
            ArcNode arcNode = iterator.next();
            int adjvex1 = arcNode.adjvex;
            curList.add(adjvex1);
        }
        // adjvex节点的邻居节点
        while (iterator_adjvex.hasNext()){
            ArcNode arcNode = iterator_adjvex.next();
            int adjvex1 = arcNode.adjvex;
            adjvexList.add(adjvex1);
        }
        // 计算公共点的数量
        int count = 0;
        int minDeg = 0;
        if (!Collections.disjoint(curList, adjvexList)){
            for (Integer each : curList){
                if (adjvexList.contains(each)){
                    count++;
                }
            }
        }else
            count = 0;
        //计算度的最小值
        if (curList.size()>adjvexList.size()){
            minDeg = adjvexList.size();
        }else {
            minDeg = curList.size();
        }
        return new int[]{count, minDeg};
    }

    /**
     * 随机游走过程
     * @param start 起始点位置
     * @param k 达到稳态后的采样个数
     */
    private void RandomWalk(int start, int k){
        boolean isBanance = false;   //是否达到稳态状态
        int currentNodeIndex;
        currentNodeIndex = start;
        List<Double> vectorBefore;    //前一状态的向量
        List<Double> vectorAfter;     //后一状态的向量
        Set<Integer> NodeCollect = new HashSet<>();  //采样得到的点的序号
        int i = 0;
        long starttime = System.currentTimeMillis();
        while (i < 10000){
            vectorBefore = getVector();
            System.out.println(vectorBefore.toString());
            System.out.println("走了" + (i+1) + "条路");
            System.out.println(System.currentTimeMillis()-starttime + "ms");
            //随机选择下一跳节点，用拒绝的方式
            int nextNodeIndex = nextNode(currentNodeIndex);
            //计算下一跳节点的向量概率
            VexNode<NodeCNARW> currentNode = graph.GetDataByIndex(currentNodeIndex);
            VexNode<NodeCNARW> nextNode = graph.GetDataByIndex(nextNodeIndex);
            String path = currentNode.data.getNode_name()+"->"+nextNode.data.getNode_name();
            AllPath.add(path);
            System.out.println(path);
            /**----------------------第一种处理方式，符合矩阵乘法的逻辑-----------------------*/
            // 更新下一跳节点，暂时记录下来
            double nextNodeValue = getSumToNode(nextNodeIndex);
            //根据当前的还未更新新点的向量去更新其他图上所有点，暂时记录下来
            double[] tempNodeValue = new double[P_value.length];
            for (int w = 0 ; w < this.nodeNum ; ++w){
                if (w != nextNodeIndex){
                    tempNodeValue[w] = getSumToNode(w);
                }
            }
            // 更新下一跳以及所有点
            for (int w = 0 ; w < this.nodeNum ; ++w){
                ((NodeCNARW)graph.GetDataByIndex(w).data).setNode_values(tempNodeValue[w]);
            }
            nextNode.data.setNode_values(nextNodeValue);
            /**-------------------第二种处理方式，用新的点去更新其他的向量-----------------------*/
            /**ps:这种方法有问题，最终达到稳态的时候，所有点的值相加并不等于1，因为和矩阵的乘法没有对应上的原因*/
//            // 更新下一跳节点，直接更新
//            double nextNodeValue = getSumToNode(nextNodeIndex);
//            nextNode.data.setNode_values(nextNodeValue);
//            //根据当前的还未更新新点的向量去更新其他图上所有点，暂时记录下来
//            double[] tempNodeValue = new double[P_value.length];
//            for (int w = 0 ; w < this.nodeNum ; ++w){
//                if (w != nextNodeIndex){
//                    tempNodeValue[w] = getSumToNode(w);
//                }
//            }
//            // 更新除下一跳以外的所有点
//            for (int w = 0 ; w < this.nodeNum ; ++w){
//                if (w != nextNodeIndex){
//                    ((NodeCNARW)graph.GetDataByIndex(w).data).setNode_values(tempNodeValue[w]);
//                }
//            }

            vectorAfter = getVector();
            System.out.println(vectorAfter.toString());
            currentNodeIndex = nextNodeIndex;
            i++;
//            // 判断是否达到稳态，若达到稳态，则开始采样。达到想要采样的个数后停止随机游走
//            if (IsBanance(vectorBefore, vectorAfter)){
//                isBanance = true;
//            }
//            if (isBanance){
//                System.out.println("稳态 " + path);
//                if (NodeCollect.size() < k){
//                    System.out.println("加入点" + currentNodeIndex);
//                    NodeCollect.add(currentNodeIndex);
//                }else {
//                    break;
//                }
//            }
        }
        // 开始得到估计值
        double sumf = 0.0;
        double sum = 0.0;
        for (Integer each : NodeCollect){
            double weight = getWeight(each);
            int f = getDegree(each);      //这边的f指的是度
            sumf+=(weight * f);
            sum+=weight;
        }
        System.out.println("估计值为：" + sumf/sum);
    }

    /**
     * 计算连接到所有点的和（类似于模拟矩阵的运算）
     * @param nodeIndex
     * @return
     */
    private double getSumToNode(int nodeIndex){
        Iterator<Graph.ArcNode> iterator = graph.iterator(nodeIndex);
        double sum = 0.0;
        while (iterator.hasNext()){
            ArcNode arcNode = iterator.next();
//            double p_uvValue = getP_uvValue(arcNode.adjvex, nodeIndex);
            double p_uvValue = getP_uvValueByMatrix(arcNode.adjvex, nodeIndex);
            VexNode<NodeCNARW> currentNode = graph.GetDataByIndex(arcNode.adjvex);
            sum+=(p_uvValue*currentNode.data.getNode_values());
        }
        return sum;
    }

    /**
     * 得到当前向量
     * @return
     */
    private List<Double> getVector(){
        List<Double> vectorList = new ArrayList<>();
        //得到概率向量
        VexNode<NodeCNARW>[] vexNodesList = (VexNode<NodeCNARW>[]) graph.getVexNodes();
        for (int i = 0 ; i < vexNodesList.length ; ++i){
            vectorList.add(vexNodesList[i].data.node_values);
        }
//        System.out.println(vectorList.toString());
        return vectorList;
    }

    /**
     * 随机选择下一跳节点 （拒绝还是接受）
     * @param currentNode
     * @return
     */
    private int nextNode(int currentNode){
        int nodeIndex;
        while (true){
            int index = graph.outNearNodesRandom(currentNode);
            double p_uvValue = getP_uvValue(currentNode, index);
            // 生成0到1的随机数
            double random = Math.random();
            if (random <= p_uvValue){
                nodeIndex = index;
                break;
            }
        }
        return nodeIndex;
    }

    /**
     * 判断当前向量是否达到稳态
     * @param vectorBefore
     * @param vectorAfter
     * @return
     */
    private boolean IsBanance(List<Double> vectorBefore, List<Double> vectorAfter){
        for (int i = 0 ; i < vectorBefore.size() ; ++i){
            if (Math.abs(vectorAfter.get(i)-vectorBefore.get(i))>0.00001){
                return false;
            }
        }
        return true;
    }

    /**
     * 路径的统计
     * @param times
     */
    public void PathStatistics(int times){
        List<String> listBefore = new ArrayList<>();
        List<String> listAfter = new ArrayList<>();
        for (int i = 0 ; i < AllPath.size() ; ++i){
            if (i<times){
                listBefore.add(AllPath.get(i));
                if (!PathBefore.containsKey(AllPath.get(i))){
                    PathBefore.put(AllPath.get(i), 1);
                }else {
                    PathBefore.put(AllPath.get(i), PathBefore.get(AllPath.get(i))+1);
                }
            }else {
                listAfter.add(AllPath.get(i));
                if (!PathAfter.containsKey(AllPath.get(i))){
                    PathAfter.put(AllPath.get(i), 1);
                }else {
                    PathAfter.put(AllPath.get(i), PathAfter.get(AllPath.get(i))+1);
                }
            }
        }

        System.out.println(listBefore.toString());
        System.out.println(listAfter.toString());
        System.out.println("-----------------------------");
        for (Map.Entry<String, Integer> each : PathBefore.entrySet()){
            System.out.println(each.getKey()+":"+each.getValue());
        }
        System.out.println("--------------------------");
        for (Map.Entry<String, Integer> each : PathAfter.entrySet()){
            System.out.println(each.getKey()+":"+each.getValue());
        }
    }

    /**
     * 得到图中真实的值，与估计值对比。这边函数取的是计算节点的平均度
     * @return
     */
    public double getTrueValue(){
        double sum = 0.0;
        for (int i = 0 ; i < graph.getVexNodes().length ; ++i){
//            System.out.println(getDegree(i));
            sum+=getDegree(i);
        }
        return sum/graph.getVexNodes().length;
    }

    public static void main(String[] args) {
        CNARW cn = new CNARW();
        cn.initGraph();
//        cn.CalTransitionMatrix(0);
//        for (int i = 0 ; i < 10 ; ++i){
//            List<Double> doubleList = new ArrayList<>();
//            for ( int j = 0 ; j < 10 ; ++j){
//                double p_uvValue1 = cn.getP_uvValue(i, j);
//                doubleList.add(p_uvValue1);
//            }
//            System.out.println(doubleList.toString());
//        }
//        System.out.println(cn.nextNode(0));
        // 从第几个点开始随机游走，在达到稳态之后取几个样本点
        cn.RandomWalk(0, 6);
        System.out.println("度的真实值为：" + cn.getTrueValue());
    }
}