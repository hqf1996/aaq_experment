package com.TransEOutput;

import com.util.BaseOperate;
import com.util.EdgeSource;
import com.util.SqlUtil;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hqf
 * @description: 从数据库中随机取元素
 * @Data: Create in 12:56 2019/11/8
 * @Modified By:
 */
public class Output1 extends BaseOperate {
    private String[] columns;
    private static BufferedWriter RDFWriter;

    public Output1(String tableName) throws FileNotFoundException {
        super(tableName);
        columns = new String[]{"entity_id1", "entity_id2", "entity_name1", "predicate_name", "entity_name2"};
        RDFWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\dbpedia_all_graph\\RDF\\limitAllGraph\\rdf.txt")));

    }

    @Override
    public List<EdgeSource> selectLimit(String limit) {
        ResultSet result = null ;
        String sql = null;
        List<EdgeSource> list = new ArrayList<>();
        try {
            sql = getSelectSql(limit);
            result = stmt.executeQuery(sql);
            while (result.next()){
                EdgeSource edgeSource = new EdgeSource();
                edgeSource.setId(result.getInt("id"));
                edgeSource.setEntity_id1(result.getString("entity_id1"));
                edgeSource.setEntity_id2(result.getString("entity_id2"));
                edgeSource.setEntity_name1(result.getString("entity_name1"));
                edgeSource.setPredicate_name(result.getString("predicate_name"));
                edgeSource.setEntity_name2(result.getString("entity_name2"));
                list.add(edgeSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(sql);
        }finally {
            SqlUtil.close(result);
            return list;
        }
    }

    /**
     * 取出数据库中除了type以外的所有谓词
     */
    public List<String> selectAllType() {
        ResultSet result = null ;
        String sql = null;
        List<String> list = new ArrayList<>();
        try {
            sql = "select predicate_name from edge_no_string_complete_source WHERE predicate_name!='type' GROUP BY predicate_name;";
            result = stmt.executeQuery(sql);
            while (result.next()){
                String predicate = result.getString("predicate_name");
                list.add(predicate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(sql);
        }finally {
            SqlUtil.close(result);
            return list;
        }
    }

    /**
     * 从数据库中随机抽取出不同类型的RDF，限定条数
     * @param predicate
     * @param numLimit
     */
    public void selectRandomRDF(String predicate, int numLimit) throws IOException {
        ResultSet result = null ;
        String sql = null;
        try {
            sql = getSelectSql("where predicate_name=\""+predicate+"\" limit " + numLimit);
//            System.out.println(sql);
            result = stmt.executeQuery(sql);
            int count = 0;
            while (result.next()){
                count++;
                EdgeSource edgeSource = new EdgeSource();
                edgeSource.setEntity_id1(result.getString("entity_id1"));
                edgeSource.setEntity_id2(result.getString("entity_id2"));
                edgeSource.setEntity_name1(result.getString("entity_name1"));
                edgeSource.setPredicate_name(result.getString("predicate_name"));
                edgeSource.setEntity_name2(result.getString("entity_name2"));
                RDFWriter.write(edgeSource.toString()+"\n");
            }
            System.out.println(count+":"+predicate);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println(sql);
        }finally {
            SqlUtil.close(result);
        }
    }

    public static void main(String[] args) throws IOException {
        Output1 output1 = new Output1("edge_no_string_complete_source");
        List<String> AllPredicate = output1.selectAllType();
        System.out.println(AllPredicate.size());
        for (String s : AllPredicate) {
            output1.selectRandomRDF(s, 1520000);
        }
        RDFWriter.close(); //1520221
    }
}
