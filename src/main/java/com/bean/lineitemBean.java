package com.bean;

import java.util.Date;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 11:17 2019/8/8
 * @Modified By:
 */
public class lineitemBean {
    private int orderkey;
    private int partkey;
    private int suppkey;
    private int linenumber;
    private double quantity;
    private double extendedprice;
    private double discount;
    private double tax;
    private String returnflag;
    private String linestatus;
    private String shipdate;
    private String commitdate;
    private String receiptdate;
    private String shipinstruct;
    private String shipmode;
    private String comment;

    public lineitemBean(int orderkey, int partkey, int suppkey, int linenumber, double quantity, double extendedprice, double discount, double tax, String returnflag, String linestatus, String shipdate, String commitdate, String receiptdate, String shipinstruct, String shipmode, String comment) {
        this.orderkey = orderkey;
        this.partkey = partkey;
        this.suppkey = suppkey;
        this.linenumber = linenumber;
        this.quantity = quantity;
        this.extendedprice = extendedprice;
        this.discount = discount;
        this.tax = tax;
        this.returnflag = returnflag;
        this.linestatus = linestatus;
        this.shipdate = shipdate;
        this.commitdate = commitdate;
        this.receiptdate = receiptdate;
        this.shipinstruct = shipinstruct;
        this.shipmode = shipmode;
        this.comment = comment;
    }

    public lineitemBean(String []split) {
        this.orderkey = Integer.valueOf(split[0]);
        this.partkey = Integer.valueOf(split[1]);
        this.suppkey = Integer.valueOf(split[2]);
        this.linenumber = Integer.valueOf(split[3]);
        this.quantity = Double.valueOf(split[4]);
        this.extendedprice = Double.valueOf(split[5]);
        this.discount = Double.valueOf(split[6]);
        this.tax = Double.valueOf(split[7]);
        this.returnflag = split[8];
        this.linestatus = split[9];
        this.shipdate = split[10];
        this.commitdate = split[11];
        this.receiptdate = split[12];
        this.shipinstruct = split[13];
        this.shipmode = split[14];
        this.comment = split[15];
    }

    @Override
    public String toString() {
        return "lineitemBean{" +
                "orderkey=" + orderkey +
                ", partkey=" + partkey +
                ", suppkey=" + suppkey +
                ", linenumber=" + linenumber +
                ", quantity=" + quantity +
                ", extendedprice=" + extendedprice +
                ", discount=" + discount +
                ", tax=" + tax +
                ", returnflag='" + returnflag + '\'' +
                ", linestatus='" + linestatus + '\'' +
                ", shipdate='" + shipdate + '\'' +
                ", commitdate='" + commitdate + '\'' +
                ", receiptdate='" + receiptdate + '\'' +
                ", shipinstruct='" + shipinstruct + '\'' +
                ", shipmode='" + shipmode + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(int orderkey) {
        this.orderkey = orderkey;
    }

    public int getPartkey() {
        return partkey;
    }

    public void setPartkey(int partkey) {
        this.partkey = partkey;
    }

    public int getSuppkey() {
        return suppkey;
    }

    public void setSuppkey(int suppkey) {
        this.suppkey = suppkey;
    }

    public int getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(int linenumber) {
        this.linenumber = linenumber;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getExtendedprice() {
        return extendedprice;
    }

    public void setExtendedprice(double extendedprice) {
        this.extendedprice = extendedprice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getReturnflag() {
        return returnflag;
    }

    public void setReturnflag(String returnflag) {
        this.returnflag = returnflag;
    }

    public String getLinestatus() {
        return linestatus;
    }

    public void setLinestatus(String linestatus) {
        this.linestatus = linestatus;
    }

    public String getShipdate() {
        return shipdate;
    }

    public void setShipdate(String shipdate) {
        this.shipdate = shipdate;
    }

    public String getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(String commitdate) {
        this.commitdate = commitdate;
    }

    public String getReceiptdate() {
        return receiptdate;
    }

    public void setReceiptdate(String receiptdate) {
        this.receiptdate = receiptdate;
    }

    public String getShipinstruct() {
        return shipinstruct;
    }

    public void setShipinstruct(String shipinstruct) {
        this.shipinstruct = shipinstruct;
    }

    public String getShipmode() {
        return shipmode;
    }

    public void setShipmode(String shipmode) {
        this.shipmode = shipmode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
