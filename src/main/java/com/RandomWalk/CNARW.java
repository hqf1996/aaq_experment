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
    /**
     * 初始化图
     * @return
     */
    private void initGraph(){
        Graph<NodeCNARW, Double> graph = new Graph<>(false);
        String []nodes_names = {"u" ,"a", "b", "c", "d", "v", "e", "f", "g", "h"};
        double []nodes_values = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        NodeCNARW []nodes = new NodeCNARW[10];
        for (int i = 0 ; i < nodes_names.length ; ++i){
            nodes[i] = new NodeCNARW(nodes_names[i], nodes_values[i]);
        }

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
    }


    /**
     * 计算出转移矩阵
     * @param start 从哪个点开始做一次bfs
     */
    private void CalTransitionMatrix(int start){
        initGraph();
        Set<Integer> hasVisited = new HashSet<>(); //已经遍历过的点
        Set<Integer> currentVisited = new HashSet<>(); //正在遍历队列中 还没有遍历的点
        currentVisited.add(0);  //从u点开始
        while (!currentVisited.isEmpty()){
            Set<Integer> NextNode = new HashSet<>();
            for (Integer cur : currentVisited){
                hasVisited.add(cur);
                Iterator<Graph.ArcNode> iterator = graph.iterator(cur);
                while (iterator.hasNext()){
                    ArcNode arcNode = iterator.next();
                    int adjvex = arcNode.adjvex;
                    int[] result = getPublicNodeNumAndDeg(iterator, adjvex);
                    int C = result[0];  //公共点数
                    int minDeg = result[1];  //度的最小值
                    // 计算quv
                    double q = 1-1.0*C/minDeg;

                }
            }
        }
    }

    /**
     * 拿到cur 和 adjvex两个点的共有点以及两个点度的最小值
     * @param iterator
     * @param adjvex
     * @return
     */

    private int[] getPublicNodeNumAndDeg(Iterator<Graph.ArcNode> iterator, int adjvex) {
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

    public static void main(String[] args) {
        CNARW cn = new CNARW();
        cn.CalTransitionMatrix(0);
    }

}
