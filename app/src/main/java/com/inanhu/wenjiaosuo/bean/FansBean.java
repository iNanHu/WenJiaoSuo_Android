package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * Created by Jason on 2016/7/28.
 */
public class FansBean implements Serializable {


    /**
     * uid : 1
     * level : 1
     * name : name
     * telphone : 134****1234
     */

    private String uid;
    private String level; // 粉丝等级，一级和二级
    private String name; // 粉丝真实姓名
    private String telphone; // 粉丝电话号码

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
