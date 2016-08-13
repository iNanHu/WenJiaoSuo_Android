package com.inanhu.wenjiaosuo.bean;

import java.io.Serializable;

/**
 * 用户信息（用于本地保存）
 * <p/>
 * Created by iNanHu on 2016/7/21.
 */
public class UserInfo implements Serializable {


    /**
     * username : 13761245954
     * email : bagecelia@163.com
     * invite_number : c73d28cd
     * login_time : 1469113563
     * uid : 20
     * realname : 缪振正
     * sex : 1
     * certificate_type : 身份证
     * certificate_number : 320623199112060808
     * telphone : 13761245954
     * address : 上海市徐汇区三旋路
     * bank : 中国银行
     * account_number : 622848645214332
     * bank_location : 江苏南通
     * branch_name : 通知支行
     * certificate_front_image : http://wmyzt-wmyzt.stor.sinaapp.com/images%2F2016-07-16%2F9d9d975f4e0afedc7bed6e5929ac1b77.jpg
     * certificate_back_image : http://wmyzt-wmyzt.stor.sinaapp.com/images%2F2016-07-16%2F3131431c5e8bc6b22f0d236b00de9f75.jpg
     * bank_card_image : http://wmyzt-wmyzt.stor.sinaapp.com/images%2F2016-07-16%2F7316baa052a5ae5e121d5a94b75bd6fc.jpg
     */

    private String username; // 用户名也就是手机号
    private String email;
    private String invite_number;
    private String login_time;
    private String rank;
    private String avatar;
    private String uid;
    private String realname;
    private String sex;
    private String certificate_type;
    private String certificate_number;
    private String telphone;
    private String address;
    private String bank;
    private String account_number;
    private String bank_location;
    private String branch_name;
    private String certificate_front_image;
    private String certificate_back_image;
    private String bank_card_image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvite_number() {
        return invite_number;
    }

    public void setInvite_number(String invite_number) {
        this.invite_number = invite_number;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_location() {
        return bank_location;
    }

    public void setBank_location(String bank_location) {
        this.bank_location = bank_location;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getCertificate_front_image() {
        return certificate_front_image;
    }

    public void setCertificate_front_image(String certificate_front_image) {
        this.certificate_front_image = certificate_front_image;
    }

    public String getCertificate_back_image() {
        return certificate_back_image;
    }

    public void setCertificate_back_image(String certificate_back_image) {
        this.certificate_back_image = certificate_back_image;
    }

    public String getBank_card_image() {
        return bank_card_image;
    }

    public void setBank_card_image(String bank_card_image) {
        this.bank_card_image = bank_card_image;
    }
}
