package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * Created by zzmiao on 2015/10/23.
 */
public class NewsBean implements Serializable {
    /**
     * 资讯图片地址
     */
    private String pic;
    /**
     * 资讯标题
     */
    private String title;
    /**
     * 资讯内容简介
     */
    private String intro;
    /**
     * 资讯发布时间
     */
    private String date;
    /**
     * 阅读次数
     */
    private String readtimes;
    /**
     * 评论数
     */
    private String comments;
    /**
     * 文章地址
     */
    private String link;

    public NewsBean() {

    }

    public NewsBean(String pic, String title, String intro, String date, String readtimes, String comments) {
        this.pic = pic;
        this.title = title;
        this.intro = intro;
        this.readtimes = readtimes;
        this.comments = comments;
        this.date = date;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return "评论(" + comments + ")";
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReadtimes() {
        return "阅读(" + readtimes + ")";
    }

    public void setReadtimes(String readtimes) {
        this.readtimes = readtimes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
