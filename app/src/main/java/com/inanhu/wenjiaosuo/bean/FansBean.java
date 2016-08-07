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
    private String username;
    private String level; // 粉丝等级，一级和二级
    private String realname; // 粉丝真实姓名
    private String telphone; // 粉丝电话号码

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
