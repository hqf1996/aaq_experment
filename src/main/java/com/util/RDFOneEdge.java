package com.util;

import java.util.Objects;

/**
 * @Author: hqf
 * @description: 只有type 和 id属性，为了TransE生成对应的输入格式的文件
 * @Data: Create in 14:53 2019/11/8
 * @Modified By:
 */
public class RDFOneEdge {
    private String id;
    private String name;

    public RDFOneEdge(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RDFOneEdge)) return false;
        RDFOneEdge that = (RDFOneEdge) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
