package com.query;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:01 2019/9/9
 * @Modified By:
 */
public class EntityStatistical {
    private int id;
    private double pss;
    private double visitedProbability;   //图稳定之后点的访问概率

    public EntityStatistical(int id, double pss) {
        this.id = id;
        this.pss = pss;
    }

    public EntityStatistical(int id, double pss, double visitedProbability) {
        this.id = id;
        this.pss = pss;
        this.visitedProbability = visitedProbability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPss() {
        return pss;
    }

    public void setPss(double pss) {
        this.pss = pss;
    }

    public double getVisitedProbability() {
        return visitedProbability;
    }

    public void setVisitedProbability(double visitedProbability) {
        this.visitedProbability = visitedProbability;
    }
}
