package com.query;

import java.util.Objects;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 20:14 2019/11/5
 * @Modified By:
 */
public class Entity {
    private int id;
    private double value;   //节点访问概率
    private int layer;  //层数

    public Entity(int id) {
        this.id = id;
    }

    public Entity(int id, double value) {
        this.id = id;
        this.value = value;
    }

    public Entity(int id, double value, int layer) {
        this.id = id;
        this.value = value;
        this.layer = layer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity that = (Entity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
