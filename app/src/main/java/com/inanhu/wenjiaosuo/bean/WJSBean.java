package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * Created by Jason on 2016/7/21.
 */
public class WJSBean implements Serializable {
    /**
     * data_id : 1
     * logo : http://www.youbicard.com/logo.jpg
     * link : http://www.youbicard.com/
     * name : 邮币卡之家
     * onekey : 2
     */

    private String data_id;
    private String logo;
    private String link; // 支持一键开户的就为官网地址，不支持的则为开户地址
    private String name;
    private String onekey; // 是否支持一键注册 1是 2 否
    private String tutorial_link; // 出金教程链接

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnekey() {
        return onekey;
    }

    public void setOnekey(String onekey) {
        this.onekey = onekey;
    }

    public String getTutorial_link() {
        return tutorial_link;
    }

    public void setTutorial_link(String tutorial_link) {
        this.tutorial_link = tutorial_link;
    }

}
