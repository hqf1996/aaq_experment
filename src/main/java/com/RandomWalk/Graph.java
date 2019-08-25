package com.RandomWalk;

import java.util.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:38 2019/1/11
 * @Modified By:
 */
public class Graph<T, K> {
    VexNode<T>[] vexNodes;    //������нڵ�
    boolean isDirected = true;    //�Ƿ�Ϊ����ͼ  Ĭ��Ϊ����ͼ

    public Graph(VexNode<T>[] vexNodes, boolean isDirected) {
        this.vexNodes = vexNodes;
        this.isDirected = isDirected;
    }

    public Graph() {
    }

    public VexNode<T>[] getVexNodes() {
        return vexNodes;
    }

    public void setVexNodes(VexNode<T>[] vexNodes) {
        this.vexNodes = vexNodes;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    /**
     * ��������ڽӱ��һ���ڵ���������ڽڵ�
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

    /***
     * ѡ��ĳ���ڵ������һ���ڽӵ�����
     */
    public int outNearNodesRandom(int start){
        if (vexNodes[start].firstarc == null){
            System.out.println("No near Nodes!");
            return -1;
        } else{
            //�洢���е��ڽӵ�
            List<ArcNode> arcNodes = new ArrayList<>();
            ArcNode cur = vexNodes[start].firstarc;
            while (cur != null){
                arcNodes.add(cur);
                cur = cur.next;
            }
            Random random = new Random();
            //����һ��[0, arcNodes.size())�������
            int i = random.nextInt(arcNodes.size());
            return arcNodes.get(i).adjvex;
        }
    }


    /**
     * ͷ�����
     * */
    class VexNode<T>{
        T data;        //��Žڵ�����
        ArcNode firstarc; //��һ���ڽӵ��λ��

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
     * �ڽӵ���
     * */
    public class ArcNode<K>{
        int adjvex;     //�ڵ��������е�λ��
        ArcNode next;     //��һ���ڽӵ�
        K edgeinfo;        //�ߵ���Ϣ

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
     * @Description: ����ͼ
     */
    public void createGraph(T[] nodes, T[][] edges, K[] predicates){
        // ���нڵ�
        vexNodes = new VexNode[nodes.length];
        for (int i = 0 ; i < nodes.length ; ++i){
            vexNodes[i] = new VexNode<>(nodes[i]);
        }
        /**
         * ���еı�
         * */
        //������ν������ƥ��
        if (edges.length != predicates.length){
            System.out.println("input error!");
        }
        // ���뵽�ڽӱ�
        for (int i = 0 ; i < edges.length ; ++i){
            insertArc(edges[i], predicates[i]);
        }
    }

    /**
     * @Author: hqf
     * @Date:
     * @Description: ����һ���ڽӵ�
     */
    private void insertArc(T[] edge, K predicate) {
//        int start = getIndex(edge[0]);
//        int end = getIndex(edge[1]);
        int start = getNodeCNARWIndex(edge[0]);
        int end = getNodeCNARWIndex(edge[1]);
//        System.out.println(start);
//        System.out.println(end);
        if (start == -1 || end == -1){
            System.out.println("Insert error!");
            return;
        }
        // ���ܲ����Լ����Լ��ı�
        if (start == end){
            System.out.println("insert error!");
            return;
        }

        // ���������ͼ�Ĳ���
        if (isDirected == true){
            ArcNode newArcNode = new ArcNode(end, predicate); //������ڵ�
            ArcNode p = vexNodes[start].firstarc;  // vexNodes[i]����һ���ڵ�
            newArcNode.next = p;
            vexNodes[start].firstarc = newArcNode;
        }
        // ����ͼ�Ĳ���
        else {
            // �������
            ArcNode newArcNode = new ArcNode(end, predicate); //������ڵ�
            ArcNode p = vexNodes[start].firstarc;  // vexNodes[i]����һ���ڵ�
            newArcNode.next = p;
            vexNodes[start].firstarc = newArcNode;
            // �������
            newArcNode = new ArcNode(start, predicate); //������ڵ�
            p = vexNodes[end].firstarc;  // vexNodes[i]����һ���ڵ�
            newArcNode.next = p;
            vexNodes[end].firstarc = newArcNode;
        }
    }

    /**
     * @Author: hqf
     * @Date:
     * @Description: �õ�һ���ڵ���vexNodes�е�λ��
     */
    private int getIndex(T t) {
        for (int i = 0 ; i < vexNodes.length ; ++i){
            if (t.equals(vexNodes[i].data)){
                return i;
            }
        }
        return -1;
    }

    /**
     * �������Ƿ���ͬ���õ�һ���ڵ���vexNodes�е�λ��
     * @param t
     * @return
     */
    private int getNodeCNARWIndex(T t){
        for (int i = 0 ; i < vexNodes.length ; ++i){
            NodeCNARW t1 = (NodeCNARW) t;
            NodeCNARW data = (NodeCNARW) vexNodes[i].data;
            if (t1.getNode_name().equals(data.getNode_name())){
                return i;
            }
        }
        return -1;
    }

    /**
     * */

    public Iterator<ArcNode> iterator(int start){
        return vexNodes[start].iterator();
    }


    public VexNode<T> GetDataByIndex(int index){
        return vexNodes[index];
    }


    /**
     * @Author: hqf
     * @Date:
     * @Description: ������ȱ���
     */
    public void BFS(int start){
        Set<Integer> hasVisited = new HashSet<>();     // �Ѿ��������ĵ�
        Set<Integer> currentVisited = new HashSet<>();     // ���ڱ���������  ��û�н��б����ĵ�
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

//        graph.outNearNodes(0);
        graph.outNearNodesRandom(0);

//        graph.BFS(5);
//        Iterator<Graph.ArcNode> iterator = graph.iterator(5);
//        while (iterator.hasNext()){
//            System.out.println(iterator.next().adjvex);
//        }
    }

}