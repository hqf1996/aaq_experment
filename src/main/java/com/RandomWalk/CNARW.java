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
    /**
     * 初始化图
     * @return
     */
    private void initGraph(){
        Graph<NodeCNARW, Double> graph = new Graph<>(false);
        String []nodes_names = {"u" ,"a", "b", "c", "d", "v", "e", "f", "g", "h"};
        double []nodes_values = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
        NodeCNARW []nodes = new NodeCNARW[10];
        for (int i = 0 ; i < nodes_names.length ; ++i){
            nodes[i] = new NodeCNARW(nodes_names[i], nodes_values[i]);
        }

        NodeCNARW [][]edges = {{new NodeCNARW("u", 0.1), new NodeCNARW("a", 0.1)},
                {new NodeCNARW("u", 0.1), new NodeCNARW("b", 0.1)},
                {new NodeCNARW("u", 0.1), new NodeCNARW("c", 0.1)},
                {new NodeCNARW("u", 0.1), new NodeCNARW("d", 0.1)},
                {new NodeCNARW("u", 0.1), new NodeCNARW("v", 0.1)},
                {new NodeCNARW("a", 0.1), new NodeCNARW("b", 0.1)},
                {new NodeCNARW("b", 0.1), new NodeCNARW("d", 0.1)},
                {new NodeCNARW("b", 0.1), new NodeCNARW("e", 0.1)},
                {new NodeCNARW("d", 0.1), new NodeCNARW("c", 0.1)},
                {new NodeCNARW("c", 0.1), new NodeCNARW("f", 0.1)},
                {new NodeCNARW("c", 0.1), new NodeCNARW("h", 0.1)},
                {new NodeCNARW("v", 0.1), new NodeCNARW("g", 0.1)},
                {new NodeCNARW("v", 0.1), new NodeCNARW("e", 0.1)},
                {new NodeCNARW("v", 0.1), new NodeCNARW("f", 0.1)},
                {new NodeCNARW("e", 0.1), new NodeCNARW("g", 0.1)},
                {new NodeCNARW("g", 0.1), new NodeCNARW("h", 0.1)}};
        Double[] predicates = {0.16216216216216217, 0.16216216216216217, 0.24324324324324323, 0.10810810810810813, 0.32432432432432434,
                0.5, 0.25, 0.37499999999999994, 0.4, 0.2926829268292683, 0.2926829268292683, 0.2, 0.2, 0.3, 0.28571428571428575, 0.42857142857142855};
        graph.createGraph(nodes, edges, predicates);
        this.graph = graph;
    }


    /**
     * 计算出转移矩阵
     * @param start 从哪个点开始做一次bfs
     */
    private void CalTransitionMatrix(int start){
        Set<Integer> hasVisited = new HashSet<>(); //已经遍历过的点
        Set<Integer> currentVisited = new HashSet<>(); //正在遍历队列中 还没有遍历的点
        currentVisited.add(0);  //从u点开始
        while (!currentVisited.isEmpty()){
            Set<Integer> NextNode = new HashSet<>();
            for (Integer cur : currentVisited){
                hasVisited.add(cur);
                Iterator<Graph.ArcNode> iterator = graph.iterator(cur);
                List<Integer> nextAdjvex = new ArrayList<>();
                // 获取当前节点的所有临界点的adjvex
                while (iterator.hasNext()){
                    ArcNode arcNode = iterator.next();
                    int adjvex = arcNode.adjvex;
                    nextAdjvex.add(adjvex);
                    if (!hasVisited.contains(adjvex)){
                        NextNode.add(adjvex);
                    }
                }
                //计算当前节点所有边的q值和，可以重用，就不用重复计算
                double q_sum = 0.0;
                for (Integer each : nextAdjvex){
                    int[] result = getPublicNodeNumAndDeg(cur, each);
                    int C = result[0];  //公共点数
                    int minDeg = result[1];  //度的最小值
                    q_sum += (1-1.0*C/minDeg);
                }
                // 计算quv
                for (Integer each : nextAdjvex){
                    int[] result = getPublicNodeNumAndDeg(cur, each);
                    int C = result[0];  //公共点数
                    int minDeg = result[1];  //度的最小值
                    double q_uv = 1-1.0*C/minDeg;
                    double p_uv_ba = 1.0*q_uv/getDegree(cur);
                    double p_uu_ba = 1-(1.0/getDegree(cur))*(q_sum);
                    double P_uv = p_uv_ba/(1-p_uu_ba);
                    VexNode<NodeCNARW> nodeCNARWVexNode1 = graph.GetDataByIndex(cur);
                    VexNode<NodeCNARW> nodeCNARWVexNode2 = graph.GetDataByIndex(each);
                    System.out.println(nodeCNARWVexNode1.data.node_name+"->"+nodeCNARWVexNode2.data.node_name + ":" + P_uv);
                }
            }
            System.out.println("___________________");
            currentVisited = NextNode;
        }
    }

    /**
     * 返回两个点的P值
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
     * @param start
     */
    private void RandomWalk(int start){
        int currentNodeIndex;
        currentNodeIndex = start;
        int i = 0;
        while (i < 1000){
            System.out.println("走了" + (i+1) + "条路");
            //随机选择下一跳节点，用拒绝的方式
            int nextNodeIndex = nextNode(currentNodeIndex);
            //更新下一跳节点的向量概率
            VexNode<NodeCNARW> currentNode = graph.GetDataByIndex(currentNodeIndex);
            VexNode<NodeCNARW> nextNode = graph.GetDataByIndex(nextNodeIndex);
            String path = currentNode.data.getNode_name()+"->"+nextNode.data.getNode_name();
            AllPath.add(path);
//            System.out.println(path);
            nextNode.data.setNode_values(getSumToNode(nextNodeIndex));
            getVector();
            currentNodeIndex = nextNodeIndex;
            i++;
        }
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
            double p_uvValue = getP_uvValue(arcNode.adjvex, nodeIndex);
            VexNode<NodeCNARW> currentNode = graph.GetDataByIndex(arcNode.adjvex);
            sum+=(p_uvValue*currentNode.data.getNode_values());
        }
        return sum;
    }

    private List<Double> getVector(){
        List<Double> vectorList = new ArrayList<>();
        //得到概率向量
        VexNode<NodeCNARW>[] vexNodesList = (VexNode<NodeCNARW>[]) graph.getVexNodes();
        for (int i = 0 ; i < vexNodesList.length ; ++i){
            vectorList.add(vexNodesList[i].data.node_values);
        }
        System.out.println(vectorList.toString());
        return vectorList;
    }

    /**
     * 随机选择下一跳节点
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
        cn.RandomWalk(0);
        Scanner in = new Scanner(System.in);
        int times = in.nextInt();
        cn.PathStatistics(times);
    }
}