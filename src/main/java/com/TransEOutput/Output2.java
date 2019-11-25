package com.TransEOutput;

import com.util.RDFOneEdge;

import java.io.*;
import java.util.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 14:11 2019/11/8
 * @Modified By:
 */
public class Output2 {
    private BufferedReader RDFReader;
    private BufferedWriter Train2idWriter;
    private BufferedWriter relation2idWriter;
    private BufferedWriter entity2idWriter;


    public void TransEFormat() throws IOException {
        RDFReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\dbpedia_all_graph\\RDF\\limitAllGraph\\rdf.txt")));
        Train2idWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\dbpedia_all_graph\\RDF\\limitAllGraph\\train2id.txt")));
        relation2idWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\dbpedia_all_graph\\RDF\\limitAllGraph\\relation2id.txt")));
        entity2idWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\dbpedia_all_graph\\RDF\\limitAllGraph\\entity2id.txt")));
        Map<RDFOneEdge, Integer> allEntityMap = new LinkedHashMap<>();
        Map<String, Integer> predicateMap = new LinkedHashMap<>();
        String line;
        // 生成entity和predicate的映射文件，生成train2id.txt
        while ((line = RDFReader.readLine()) != null){
            String[] split = line.split("\t");
            RDFOneEdge entity1 = new RDFOneEdge(split[0], split[2]);
            RDFOneEdge entity2 = new RDFOneEdge(split[1], split[4]);
            String predicate = split[3];
            if (!allEntityMap.containsKey(entity1)) {
                int count = allEntityMap.size();
                allEntityMap.put(entity1, count);
            }
            if (!allEntityMap.containsKey(entity2)) {
                int count = allEntityMap.size();
                allEntityMap.put(entity2, count);
            }
            if (!predicateMap.containsKey(predicate)) {
                int count = predicateMap.size();
                predicateMap.put(predicate, count);
            }
            Train2idWriter.write(allEntityMap.get(entity1)+" "+allEntityMap.get(entity2)+" "+predicateMap.get(predicate)+"\n");
        }
        // 生成实体和关系表
        for (Map.Entry<RDFOneEdge, Integer> each : allEntityMap.entrySet()) {
            entity2idWriter.write(each.getKey().getName()+" "+each.getKey().getId()+"\t"+each.getValue()+"\n");
        }
        for (Map.Entry<String, Integer> each : predicateMap.entrySet()) {
            relation2idWriter.write(each.getKey()+"\t"+each.getValue()+"\n");
        }
        RDFReader.close();
        Train2idWriter.close();
        entity2idWriter.close();
        relation2idWriter.close();
    }

    public static void main(String[] args) throws IOException {
        Output2 output2 = new Output2();
        output2.TransEFormat();
    }
}
