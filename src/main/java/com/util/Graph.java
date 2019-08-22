package com.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:38 2019/1/11
 * @Modified By:
 */
public class Graph<T, K> {
    VexNode<T>[] vexNodes;    //存放所有节点
    boolean isDirected = true;    //是否为有向图  默认为有向图

    public Graph(VexNode<T>[] vexNodes, boolean isDirected) {
        this.vexNodes = vexNodes;
        this.isDirected = isDirected;
    }

    public Graph() {
    }

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    /**
    * 迭代输出邻接表的一个节点的所有相邻节点
    */
    public void outNearNodes(int start){
        if (vexNodes[start].firstarc == null){
            System.out.println("No near Nodes!");
            return;
        }
        else {
            ArcNode cur = vexNodes[start].firstarc;
            while (cur != null){
                System.out.println(cur.adjvex);
                cur = cur.next;
            }
        }
    }

    /**
     * 头结点类
     * */
    class VexNode<T>{
        T data;        //存放节点数据
        ArcNode firstarc; //第一个邻接点的位置

        public VexNode(T data, ArcNode firstarc) {
            this.data = data;
            this.firstarc = firstarc;
        }

        public VexNode(T data) {
            this.data = data;
        }

        private Iterator<ArcNode> iterator(){
            return new Itr();
        }

        private class Itr implements Iterator<ArcNode>{
            private ArcNode pre;
            private ArcNode current;
            Itr(){
                current = firstarc;
            }
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            @SuppressWarnings("unchecked")
            public ArcNode next() {
                if(current == null){
                    throw new NoSuchElementException();
                }
                pre = current;
                current = current.next;
                return pre;
            }
        }

    }

    /**
     * 邻接点类
     * */
    class ArcNode<K>{
        int adjvex;     //节点在数组中的位置
        ArcNode next;     //下一个邻接点
        K edgeinfo;        //边的信息

        public ArcNode(int adjvex, ArcNode next, K edgeinfo) {
            this.adjvex = adjvex;
            this.next = next;
            this.edgeinfo = edgeinfo;
        }

        public ArcNode(int adjvex, K edgeinfo) {
            this.adjvex = adjvex;
            this.edgeinfo = edgeinfo;
            this.next = null;
        }
    }

    /**
     * @Author: hqf
     * @Date:
     * @Description: 创建图
     */
    public void createGraph(T[] nodes, T[][] edges, K[] predicates){
        // 所有节点
        vexNodes = new VexNode[nodes.length];
        for (int i = 0 ; i < nodes.length ; ++i){
            vexNodes[i] = new VexNode<>(nodes[i]);
        }
        /**
         * 所有的边
         * */
        //边数与谓词数不匹配
        if (edges.length != predicates.length){
            System.out.println("input error!");
        }
        // 插入到邻接表
        for (int i = 0 ; i < edges.length ; ++i){
            insertArc(edges[i], predicates[i]);
        }
    }

    /**
     * @Author: hqf
     * @Date:
     * @Description: 插入一个邻接点
     */
    private void insertArc(T[] edge, K predicate) {
        int start = getIndex(edge[0]);
        int end = getIndex(edge[1]);
//        System.out.println(start);
//        System.out.println(end);
        if (start == -1 || end == -1){
            System.out.println("Insert error!");
            return;
        }
        // 不能插入自己到自己的边
        if (start == end){
            System.out.println("insert error!");
            return;
        }

        // 如果是有向图的插入
        if (isDirected == true){
            ArcNode newArcNode = new ArcNode(end, predicate); //待插入节点
            ArcNode p = vexNodes[start].firstarc;  // vexNodes[i]的下一个节点
            newArcNode.next = p;
            vexNodes[start].firstarc = newArcNode;
        }
        // 无向图的插入
        else {
            // 正向插入
            ArcNode newArcNode = new ArcNode(end, predicate); //待插入节点
            ArcNode p = vexNodes[start].firstarc;  // vexNodes[i]的下一个节点
            newArcNode.next = p;
            vexNodes[start].firstarc = newArcNode;
            // 反向插入
            newArcNode = new ArcNode(start, predicate); //待插入节点
            p = vexNodes[end].firstarc;  // vexNodes[i]的下一个节点
            newArcNode.next = p;
            vexNodes[end].firstarc = newArcNode;
        }
    }

    /**
     * @Author: hqf
     * @Date:
     * @Description: 得到一个节点在vexNodes中的位置
     */
    private int getIndex(T t) {
        for (int i = 0 ; i < vexNodes.length ; ++i){
            if (t.equals(vexNodes[i].data)){
                return i;
            }
        }
        return -1;
    }

    public Iterator<ArcNode> iterator(int start){
        return vexNodes[start].iterator();
    }


    /**
    * @Author: hqf
    * @Date:
    * @Description: 广度优先遍历
    */
    public void BFS(int start){
        Set<Integer> hasVisited = new HashSet<>();     // 已经遍历过的点
        Set<Integer> currentVisited = new HashSet<>();     // 正在遍历队列中  还没有进行遍历的点
        currentVisited.add(start);
        while (!currentVisited.isEmpty()){
            Set<Integer> NextNode = new HashSet<>();
            for (Integer cur : currentVisited){
                hasVisited.add(cur);
                ArcNode p = vexNodes[cur].firstarc;
                while (p != null){
                    if (!hasVisited.contains(p.adjvex)){
                        NextNode.add(p.adjvex);
                        p = p.next;
                    }
                    else{
                        p = p.next;
                    }
                }
            }
            System.out.println(hasVisited);
            currentVisited = NextNode;
        }

    }

    public static void main(String[] args) {
        Graph<String, Double> graph = new Graph<>(false);
        String []nodes = {"u" ,"a", "b", "c", "d", "v", "e", "f", "g", "h"};
        double []nodes_values = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
        String[][] edges = {{"u", "a"},
                            {"u", "b"},
                            {"u", "c"},
                            {"u", "d"},
                            {"u", "v"},
                            {"a", "b"},
                            {"b", "d"},
                            {"b", "e"},
                            {"d", "c"},
                            {"c", "f"},
                            {"c", "h"},
                            {"v", "g"},
                            {"v", "e"},
                            {"v", "f"},
                            {"e", "g"},
                            {"g", "h"}};
        Double[] predicates = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        graph.createGraph(nodes, edges, predicates);
        graph.outNearNodes(0);
//        graph.BFS(5);
//        Iterator<Graph.ArcNode> iterator = graph.iterator(5);
//        while (iterator.hasNext()){
//            System.out.println(iterator.next().adjvex);
//        }
    }

}
