package com.RandomWalk;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 18:05 2019/8/23
 * @Modified By:
 */
public class NodeCNARW {

    String node_name;
    double node_values;

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public double getNode_values() {
        return node_values;
    }

    public void setNode_values(double node_values) {
        this.node_values = node_values;
    }

    public NodeCNARW(String node_name, double node_values) {
        this.node_name = node_name;
        this.node_values = node_values;
    }
}
