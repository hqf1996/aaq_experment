package com.TransEOutput;

import com.util.Util;

import java.util.*;

/**
 * @Author: hqf
 * @description: transE格式规整
 * @Data: Create in 13:27 2019/11/20
 * @Modified By:
 */
public class TransEFormat {
    public static void main(String[] args) {
        List<String> lists = Util.readFileAbsolute("C:\\Users\\hqf\\Desktop\\实验结果\\transE全图\\iteration500\\test_20000_200.txt");
        Map<String, Double> mapResult = new HashMap<>();
        Map<String, Double> mapResultSort = new LinkedHashMap<>();
        String head = null;
        for (String list : lists) {
            if (list.contains("====================")) {
                head = list;
                continue;
            } else {
                String[] split = list.split(" : ")[1].split("-------------");
                mapResult.put(split[0], 1-Double.parseDouble(split[1]));
            }
        }

        mapResult.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(x->mapResultSort.put(x.getKey(), x.getValue()));

        List<String> listOut = new LinkedList<>();
        listOut.add(head);
        int i = 1;
        for (Map.Entry<String, Double> each : mapResultSort.entrySet()) {
            listOut.add("第"+i+"位 : "+each.getKey()+"-------------"+each.getValue());
            i++;
        }

        Util.writeCollectionAbsolute("C:\\Users\\hqf\\Desktop\\实验结果\\transE全图\\iteration500\\test_20000_200_new.txt", listOut);
    }
}
