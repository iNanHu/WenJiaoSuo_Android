package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * Created by Jason on 2016/7/21.
 */
public class WJSStatusBean implements Serializable {


    /**
     * wjsid : 1
     * wjspass :
     * status : 2
     * wjsusername :
     */

    private String wjsid;
    private String logo;
    private String name;
    private String wjspass; // 初始密码
    private String status; // 文交所开户申请状态
    private String wjsusername; // 初始账号

    public String getWjsid() {
        return wjsid;
    }

    public void setWjsid(String wjsid) {
        this.wjsid = wjsid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWjspass() {
        return wjspass;
    }

    public void setWjspass(String wjspass) {
        this.wjspass = wjspass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWjsusername() {
        return wjsusername;
    }

    public void setWjsusername(String wjsusername) {
        this.wjsusername = wjsusername;
    }
}
