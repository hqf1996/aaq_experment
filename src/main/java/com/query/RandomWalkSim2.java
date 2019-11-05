package com.query;

import com.util.Util;

import java.io.*;
import java.util.*;

/**
 * @Author: hqf
 * @description: 从全图中框定一个子图，再进行后面的随机游走和估计等操作。
 * @Data: Create in 23:28 2019/10/28
 * @Modified By:
 */
public class RandomWalkSim2 {
    BufferedReader EntityReader;
//    BufferedReader OffsetReader;
    BufferedReader PartOfAdjTableReader;
    RandomAccessFile raf;
    Map<Integer, String> EntityMap;           // 实体索引
    Map<Integer, String> EntityTypeMap;       //实体类型索引
//    Map<Integer, Integer> OffsetMap;        // 偏移量索引
    Map<Integer, String> PartOfAdjTableMap;   // 先加载部分邻接表信息到内存中，提高查询速度  先加载1/4试试内存情况
    Map<String, Double> mapPredicate;         // 谓词对应的相似度表
    double threshold = 0.1;                   // 谓词的阈值
    double thresholdPss = 0.85;               // pss值的阈值
    /**
     * 从全图的锚点出发，限定跳数的bfs获取子图
     * @param startNode 出发节点
     * @return
     * @throws IOException
     */
    public RDFGraph createLimitBFSGraph(int startNode) throws IOException {
        EntityReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\dbpedia_all_graph\\entity.txt")));
//        OffsetReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\dbpedia_all_graph\\adjTableOffset.txt")));
        PartOfAdjTableReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\dbpedia_all_graph\\adjTable.txt")));
//        PartOfAdjTableReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\dbpedia_all_graph\\adjTable_part.txt")));

        //随机文件对象
        raf = new RandomAccessFile(new File("D:\\dbpedia_all_graph\\adjTable.txt"), "r");
        // 读入全图相似度文件，有小于0的置0
        String simFile = "C:\\Users\\hqf\\Desktop\\实验结果\\transE全图\\iteration500\\all_200.txt";
        ReadCarSimilarity read = new ReadCarSimilarity(simFile, "assembly");
        mapPredicate = read.getMap();
        for (Map.Entry<String, Double> each : mapPredicate.entrySet()) {
            if (each.getValue() < 0) {
                mapPredicate.put(each.getKey(), 0.0);
            }
        }
//        EntityReader = new BufferedReader(new InputStreamReader(new FileInputStream("/DBpedia_expertment_hqf/result/entity.txt")));
//        OffsetReader = new BufferedReader(new InputStreamReader(new FileInputStream("/DBpedia_expertment_hqf/result/adjTableOffset.txt")));
//        PartOfAdjTableReader = new BufferedReader(new InputStreamReader(new FileInputStream("/DBpedia_expertment_hqf/result/adjTable.txt")));
//        //随机文件对象
//        raf = new RandomAccessFile(new File("/DBpedia_expertment_hqf/result/adjTable.txt"), "r");

        System.out.println("开始读入内存");
        long startTime = System.currentTimeMillis();
        EntityMap = new HashMap<>();
        EntityTypeMap = new HashMap<>();
        PartOfAdjTableMap = new HashMap<>();
        String line = null;
        int count = 0;
        while ((line = EntityReader.readLine()) != null) {
            EntityMap.put(count, line);
            EntityTypeMap.put(count, line.split("\t")[2]);
            count++;
        }
        System.out.println("实体映射表和类型映射表加载到内存完毕");
        System.out.println(System.currentTimeMillis()-startTime);

        while ((line = PartOfAdjTableReader.readLine()) != null) {
            String[] split = line.split("\t");
            PartOfAdjTableMap.put(Integer.valueOf(split[0]), line);
        }
        System.out.println("部分邻接表信息加载到内存完毕");
        System.out.println(System.currentTimeMillis()-startTime);

        long bfsStartTime = System.currentTimeMillis();
        Set<Entity> entities = new HashSet<>();     //bfs得到的点
        List<Entity[]> edges = new ArrayList<>();   //bfs得到的边
        List<List<String>> edgesInfo = new ArrayList<>();    //边的信息
        Set<Integer> visitNodes = new HashSet<>();  //存储遍历过的节点
        Set<Integer> currentNodes = new HashSet<>(); //存储当前层的节点

        Map<Integer, Double> visitNodePss = new HashMap<>();  //存储当前遍历过的点的pss值
        int layer = 1;     //bfs层数
        visitNodes.add(startNode);
        currentNodes.add(startNode);
        visitNodePss.put(startNode, 1.0);
        outer:
        while (!currentNodes.isEmpty()) {
            Set<Integer> nextNodes = new HashSet<>();
            for(int currentIndex: currentNodes){
                // 得到currentIndex的邻接点。分两类：
                // 类1: 1.直接从内存中读邻接点，如果没有找到才去磁盘中根据偏移量寻找
                // 类2：1.从偏移量索引表中独出对应id的偏移量。2.从邻接表中读出对应偏移量的行。3.解析该行，获得组成图的元素。
                String[] OffsetLine = findAdjLine(currentIndex);
                // 全部读到内存中了，所以直接从map中取出
//                StringTokenizer adjLineFromRAM = findAdjLineFromRAM(currentIndex);
                Entity EntityCurrent = new Entity(currentIndex);
                entities.add(EntityCurrent);


//                adjLineFromRAM.nextToken();
//                // 从第一个谓词的位置开始
//                while (adjLineFromRAM.hasMoreTokens()) {
//                    String edgeNext = adjLineFromRAM.nextToken();
//                    double maxPredicate = getMaxPredicate(edgeNext);
//                    if (maxPredicate < threshold) {
//                        adjLineFromRAM.nextToken();
//                        continue;
//                    } else {
//                        int nodeNext = Integer.valueOf(adjLineFromRAM.nextToken());
//                        Entity visitEntity = getEntityFromId(nodeNext);
//                        entities.add(visitEntity);
//                        edges.add(new Entity[]{EntityCurrent, visitEntity});
//                        edgesInfo.add(Arrays.asList(edgeNext.split(";")));
//
//                        if (!visitNodes.contains(nodeNext)) {
//                            nextNodes.add(nodeNext);
//                        }
//                        visitNodes.add(nodeNext);
//                    }
//                }


                /** 做法一：解析currentIndex的邻接点，谓词相似度，小于某个值就不计入子图中，
                 * 类型为车计入子图中但不计入下一层候选节点中**/
                int j = 2;  //指向邻接点id的位置
                for (int i = 1 ; i < OffsetLine.length-1 ; i=i+2) {  //i指向邻接点边的位置
                    // 如果在内存中找到这个邻接点，直接返回string。否则从磁盘中查找
//                    String[] neborNodeAdj = findAdjLine(Integer.valueOf(OffsetLine[j]));
                    double maxPredicate = getMaxPredicate(OffsetLine[i]);
                    if (maxPredicate < threshold) {
                        j+=2;
                        continue;
                    } else {
                        if (layer < 3) {
                            Entity visitEntity = new Entity(Integer.valueOf(OffsetLine[j]));
                            entities.add(visitEntity);
                            edges.add(new Entity[]{EntityCurrent, visitEntity});
                            edgesInfo.add(Arrays.asList(OffsetLine[i].split(";")));
                            if (!visitNodes.contains(Integer.valueOf(OffsetLine[j])) && !EntityTypeMap.get(visitEntity.getId()).equals("automobile")) {
                                nextNodes.add(Integer.valueOf(OffsetLine[j]));
                            }
                            visitNodes.add(Integer.valueOf(OffsetLine[j]));
                        } else if (layer == 3) {
                            Entity visitEntity = new Entity(Integer.valueOf(OffsetLine[j]));
                            if (EntityTypeMap.get(visitEntity.getId()).equals("automobile")) {
                                entities.add(visitEntity);
                                edges.add(new Entity[]{EntityCurrent, visitEntity});
                                edgesInfo.add(Arrays.asList(OffsetLine[i].split(";")));
                                visitNodes.add(Integer.valueOf(OffsetLine[j]));
                            }
                        } 
                    }
//                    System.out.println(i);
                    j+=2;
                }

                /** 做法一改：所有遍历到的点，类型为车的不管相似度如何，直接加进来。其余操作和做法一相同。**/
//                int j = 2;  //指向邻接点id的位置
//                for (int i = 1 ; i < OffsetLine.length-1 ; i=i+2) {  //i指向邻接点边的位置
//                    boolean isType = false;
//                    if (EntityMap.get(Integer.valueOf(OffsetLine[j])).split("\t")[2].equals("automobile")) {
//                        isType = true;
//                    } else {
//                        isType = false;
//                        double maxPredicate = getMaxPredicate(OffsetLine[i]);
//                        if (maxPredicate < threshold) {
//                            j+=2;
//                            continue;
//                        }
//                    }
//                    //将该条邻接点记录更新到内存中
////                        PartOfAdjTableMap.put(Integer.valueOf(OffsetLine[j]), StringUtils.join(neborNodeAdj, "\t"));
//                    Entity visitEntity = getEntityFromId(Integer.valueOf(OffsetLine[j]));
//                    entities.add(visitEntity);
//                    edges.add(new Entity[]{EntityCurrent, visitEntity});
////                        System.out.println(visitEntity.toString());
//                    edgesInfo.add(Arrays.asList(OffsetLine[i].split(";")));
//
//                    // 不是车，并且没有被遍历过
//                    if (!visitNodes.contains(OffsetLine[j]) && !isType) {
//                        nextNodes.add(Integer.valueOf(OffsetLine[j]));
//                    }
//                    visitNodes.add(Integer.valueOf(OffsetLine[j]));
//
////                    System.out.println(i);
//                    j+=2;
//                }


                /** 做法二：考虑用A*的类似思想，如果类型不是车的话就不往后看一跳，否则往后看一跳，
                 * 计算估计的pss值，大于某个阈值就加入子图，否则不加入。车类型的节点加入子图不加入候选。**/
//                int j = 2;  //指向邻接点id的位置
//                for (int i = 1 ; i < OffsetLine.length-1 ; i=i+2) {  //i指向邻接点边的位置
//                    boolean isType = false;
//                    if (EntityMap.get(Integer.valueOf(OffsetLine[j])).split("\t")[2].equals("automobile")) {
//                        isType = true;
//                    } else {
//                        isType = false;
//                        double maxPredicate = getMaxPredicate(OffsetLine[i]);
//                        // 如果在内存中找到这个邻接点，直接返回string。否则从磁盘中查找
//                        String[] neborNodeAdj = findAdjLine(Integer.valueOf(OffsetLine[j]));
//                        // 找下一跳的最大谓词
//                        double maxPredicateNextJump = getMaxPredicateNextJump(neborNodeAdj);
//                        double pss = Math.pow(visitNodePss.get(currentIndex) * maxPredicate*maxPredicateNextJump, 1.0/(layer+1));
//                        if (pss < thresholdPss) {
//                            j+=2;
//                            continue;
//                        } else {
//                            visitNodePss.put(Integer.valueOf(OffsetLine[j]), Math.pow(visitNodePss.get(currentIndex) * maxPredicate, 1.0/layer));
//                        }
//                    }
//
//                    Entity visitEntity = getEntityFromId(Integer.valueOf(OffsetLine[j]));
//                    entities.add(visitEntity);
//                    edges.add(new Entity[]{EntityCurrent, visitEntity});
////                        System.out.println(visitEntity.toString());
//                    edgesInfo.add(Arrays.asList(OffsetLine[i].split(";")));
//
//                    // 不是车，并且没有被遍历过
//                    if (!visitNodes.contains(OffsetLine[j]) && !isType) {
//                        nextNodes.add(Integer.valueOf(OffsetLine[j]));
//                    }
//                    visitNodes.add(Integer.valueOf(OffsetLine[j]));
//
////                    System.out.println(i);
//                    j+=2;
//                }

                /** 做法三：在上一个做法的基础上修改，只对当前点的pss做估计，直接估计当前点的pss值，假设下一跳的相似度为1，
                 * 超过pss阈值就框定到子图中即可**/
//                int j = 2;  //指向邻接点id的位置
//                for (int i = 1 ; i < OffsetLine.length-1 ; i=i+2) {  //i指向邻接点边的位置
//                    boolean isType = false;
//                    if (EntityMap.get(Integer.valueOf(OffsetLine[j])).split("\t")[2].equals("automobile")) {
//                        isType = true;
//                    } else {
//                        // 该点不是车，把它当作三跳找到车，后面的谓词默认为1来计算pss
//                        isType = false;
//                        double maxPredicate = getMaxPredicate(OffsetLine[i]);
//                        // 假设下一跳相似度为最大值1计算出来的估计pss值
//                        double pss = Math.pow(visitNodePss.get(currentIndex) * maxPredicate, 1.0/(layer+1));
//                        if (pss < thresholdPss) {
//                            j+=2;
//                            continue;
//                        } else {
//                            visitNodePss.put(Integer.valueOf(OffsetLine[j]), Math.pow(visitNodePss.get(currentIndex) * maxPredicate, 1.0/layer));
//                        }
//                    }
//
//                    Entity visitEntity = getEntityFromId(Integer.valueOf(OffsetLine[j]));
//                    entities.add(visitEntity);
//                    edges.add(new Entity[]{EntityCurrent, visitEntity});
////                        System.out.println(visitEntity.toString());
//                    edgesInfo.add(Arrays.asList(OffsetLine[i].split(";")));
//
//                    // 不是车，并且没有被遍历过
//                    if (!visitNodes.contains(OffsetLine[j]) && !isType) {
//                        nextNodes.add(Integer.valueOf(OffsetLine[j]));
//                    }
//                    visitNodes.add(Integer.valueOf(OffsetLine[j]));
//
////                    System.out.println(i);
//                    j+=2;
//                }


            }
            layer++;
            System.out.println(layer);
            System.out.println("遍历过的点有 " + visitNodes.size());
            // 3跳以内
//            threshold += 0.05;
            if (layer == 4) {
                break outer;
            }
            currentNodes = nextNodes;
        }
        EntityReader.close();
//        OffsetReader.close();
        raf.close();
        EntityMap = null;
        PartOfAdjTableMap = null;
        RDFGraph graphRandom = new RDFGraph();
        System.out.println("子图框定消耗的时间：" + (System.currentTimeMillis()-bfsStartTime) + "ms");
//        /***导出一份到文件*/
//        List<String> entity_txt = new ArrayList<>();
//        List<String> edge_txt = new ArrayList<>();
//        for (Entity each : entities){
//            entity_txt.add(each.getId()+"\t"+each.getName()+"\t"+each.getType());
//        }
//        for (int i = 0 ; i < edges.size() ; ++i) {
//            Entity entity1 = edges.get(i)[0];
//            Entity entity2 = edges.get(i)[1];
//            List<String> values = edgesInfo.get(i);
//            for (String value : values){
//                edge_txt.add(entity1.getId() + "\t" + entity2.getId() + "\t" + entity1.getName() + "\t" + value + "\t" + entity2.getName());
//            }
//        }
//        Util.writeCollectionAbsolute("D:\\dbpedia_all_graph\\Germany\\entity.txt", entity_txt);
//        Util.writeCollectionAbsolute("D:\\dbpedia_all_graph\\Germany\\edge.txt", edge_txt);
////        Util.writeCollectionAbsolute("/DBpedia_expertment_hqf/result/Germany/entity.txt", entity_txt);
////        Util.writeCollectionAbsolute("/DBpedia_expertment_hqf/result/Germany/edge.txt", edge_txt);
//
//        long createGraphTime=  System.currentTimeMillis();
        graphRandom.createGraph(entities, edges, edgesInfo);
//        System.out.println(System.currentTimeMillis() - createGraphTime + "ms");
        return graphRandom;
//        return null;
    }



    /**
     * 读出对应id的偏移量的行
     * @param offset
     * @return
     */
    public String ReadIdOffset(int offset) throws IOException {
        raf.seek(offset);//设置指针的位置
        byte[] bytes = new byte[1000000];
        for (int i=0; i<bytes.length; i++) {
            byte tmp = raf.readByte();//每次读一个字节，并把它赋值给字节bytes[i]
            if (tmp=='\n') {
                break;
            } else {
                bytes[i] = tmp;
            }
        }
        String stringValue = new String(bytes, "utf-8");
        stringValue = stringValue.trim();
//        System.out.println("此行的长度为 " + stringValue.length());
//        System.out.println(stringValue);
//        raf.close();//关闭文件
        return stringValue;
    }

    /**
     * 根据NodeId返回实体类型
     * @param NodeId
     * @return
     */
//    public Entity getEntityFromId(int NodeId) {
//        String EntityStr = EntityMap.get(NodeId);
////        StringTokenizer st = new StringTokenizer(EntityStr, "\t");
////        Entity e = null;
////        while (st.hasMoreTokens()) {
////            e = new Entity(st.nextToken(), st.nextToken(), st.nextToken());
////        }
//        String[] split = EntityStr.split("\t");
//        Entity e = new Entity(split[0], split[1], split[2]);
//        return e;
//    }

    /**
     * 找邻接表中头结点为currentIndex的点。
     * @param currentIndex
     * @return
     * @throws IOException
     */
    public String[] findAdjLine(int currentIndex) {
        String OffsetStr;
        String[] OffsetLine;
        // 如果在内存中找到这个邻接点，直接返回string。否则从磁盘中查找
        OffsetStr = PartOfAdjTableMap.get(currentIndex);
        OffsetLine = OffsetStr.split("\t");
        return OffsetLine;
    }

    /**
     * 直接从内存中找邻接表中头结点为currentIndex的点。
     * @param currentIndex
     * @return
     */
    public StringTokenizer findAdjLineFromRAM(int currentIndex) {
        String AdjStr = PartOfAdjTableMap.get(currentIndex);
        StringTokenizer st = new StringTokenizer(AdjStr,"\t");
        return st;
    }

    /**
     * 中心点所有的谓词及其数量的统计
     * @param OffsetLine
     * @return
     */
    public Map<String, Integer> getCenterNodeEdgeCountMap(String[] OffsetLine) {
        Map<String, Integer> CenterNodeEdgeCountMap = new HashMap<>();
        for (int i = 1 ; i < OffsetLine.length ; i=i+2) {
            String[] split = OffsetLine[i].split(";");
            for (String each : split) {
                if (CenterNodeEdgeCountMap.get(each) == null) {
                    CenterNodeEdgeCountMap.put(each, 1);
                } else {
                    CenterNodeEdgeCountMap.put(each, CenterNodeEdgeCountMap.get(each)+1);
                }
            }
        }
        return CenterNodeEdgeCountMap;
    }


    /**
     * 计算谓词占比和余弦相似度
     * @param NodeEdgeCountMap 该实体周围的谓词和相应的数量
     */
    private double[] calculateSimilar(Map<String, Integer> centerNodeEdgeCountMap, Map<String, Integer> NodeEdgeCountMap){
        Map<String, Integer> similarEdgeMap = new HashMap<>();
        int fz = 0, fmX = 0, fmY = 0;
        // 计算余弦相似度的fz
        for(Map.Entry<String, Integer> each: NodeEdgeCountMap.entrySet()){
            Integer ceterValue = centerNodeEdgeCountMap.get(each.getKey());
            if(ceterValue != null) {
                similarEdgeMap.put(each.getKey(), each.getValue());
                fz += each.getValue() * ceterValue;
                fmX += each.getValue() * each.getValue();
                fmY += ceterValue*ceterValue;
            }
        }
        if(similarEdgeMap.isEmpty()){
            return new double[]{0, 0};
        }else {
            //计算雅可比相似度和余弦相似度
            return new double[]{(double)(similarEdgeMap.size())/(NodeEdgeCountMap.size()+centerNodeEdgeCountMap.size()-similarEdgeMap.size()),
                    fz/(Math.sqrt((double) fmX) * Math.sqrt((double)fmY))};
            //计算占比和余弦相似度
//            return new double[]{similarEdgeMap.size()/(centerNodeEdgeCountMap.size()*1.0), fz/(Math.sqrt(fmX*1.0) * Math.sqrt(fmY*1.0))};
        }

    }

    /**
     * 获得两个点之间的雅克比相似度
     * @param index1
     * @param index2
     */
    public void getJakebi(int index1, int index2) throws IOException {
        String[] adjLine1 = findAdjLine(index1);
        String[] adjLine2 = findAdjLine(index2);
        Map<String, Integer> map1 = getCenterNodeEdgeCountMap(adjLine1);
        Map<String, Integer> map2 = getCenterNodeEdgeCountMap(adjLine2);
        double[] similar = calculateSimilar(map1, map2);
        System.out.println(similar[0] + " " + similar[1]);
    }

    /**
     * 返回谓词中最大的相似度数
     * @param predicateStr
     * @return
     */
    public double getMaxPredicate(String predicateStr) {
        List<String> list = Arrays.asList(predicateStr.split(";"));
        double maxPredicate = 0.0;
        for (String each : list) {
            if (mapPredicate.get(each) != null) {
                if (maxPredicate < mapPredicate.get(each)) {
                    maxPredicate = mapPredicate.get(each);
                }
            } else {
                continue;
            }
        }
        return maxPredicate;
    }

    /**
     * 根据邻接点的信息来判断下一跳的最大谓词值
     * @param adjLine
     * @return
     */
    public double getMaxPredicateNextJump(String[] adjLine) {
        double maxPredicate = 0.0;
        for (int i = 1 ; i < adjLine.length ; i=i+2) {
            double maxPredicateTmp = getMaxPredicate(adjLine[i]);
            if (maxPredicate < maxPredicateTmp) {
                maxPredicate = maxPredicateTmp;
            }
        }
        return maxPredicate;
    }

    public static void main(String[] args) throws IOException {
        RandomWalkSim2 randomWalkSim2 = new RandomWalkSim2();
        randomWalkSim2.createLimitBFSGraph(1790907);
//        randomWalkSim2.getJakebi(1790907, 3335317);
//        randomWalkSim2.getJakebi(1790907, 4053798);
//        randomWalkSim2.getJakebi(1790907, 992795);
//        randomWalkSim2.getJakebi(1790907, 4271281);
//        randomWalkSim2.getJakebi(1790907, 2099339);
    }

}
