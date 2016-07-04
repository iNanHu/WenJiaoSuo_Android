package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * Created by Jason on 2016/7/4.
 */
public class EquityDataBean implements Serializable {

    /**
     * total : 152
     * up : 79
     * down : 25
     * flat : 0
     * money : 2208224993.00
     * count : 5505801
     * top : 8638.47
     * bottom : 8358.31
     * eid : 38
     * grades :
     * dd :
     * price : 8605.93
     * rate : 2.023
     */

    private String total; // 藏品总数
    private String up; // 藏品上涨数量
    private String down; // 藏品下跌数量
    private String flat; // 藏品平盘数量
    private String money; // 成交总额
    private String count; // 成交总量
    private String top; // 最高指数
    private String bottom; // 最低指数
    private String eid; // 文交所id
    private String grades; //
    private String dd; //
    private String price; // 综合指数
    private String rate; // 上涨/下跌率

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
