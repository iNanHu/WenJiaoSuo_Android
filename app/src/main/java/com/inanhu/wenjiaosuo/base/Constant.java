package com.inanhu.wenjiaosuo.base;

/**
 * Created by Jason on 2016/6/29.
 */
public class Constant {

    public class RequestKey {
        // 用户token
        public static final String ACCESS_TOKEN = "access-token";

        // 用户注册/登录
        public static final String USERNAME = "username";
        public static final String NAME = "name";
        public static final String REGISTER_PASSWORD = "password";
        public static final String LOGIN_PASSWORD = "pass";
        public static final String EMAIL = "email";
        public static final String INVITE = "invite";
        public static final String CODE = "code";

        // 文件上传
        public static final String UPFILE = "file";
        public static final String CERTIFICATE_FRONT = "certificate_front";
        public static final String CERTIFICATE_BACK = "certificate_back";
        public static final String BANK_CARD = "bank_card";

        // 完善用户信息
        public static final String REALNAME = "realname";
        public static final String SEX = "sex";
        public static final String CERTIFICATE_TYPE = "certificate_type";
        public static final String CERTIFICATE_NUMBER = "certificate_number";
        public static final String TELPHONE = "telphone";
        public static final String ADDRESS = "address";
        public static final String BANK = "bank";
        public static final String ACCOUNT_NUMBER = "account_number";
        public static final String BANK_LOCATION = "bank_location";
        public static final String BRANCH_NAME = "branch_name";
        public static final String CERTIFICATE_FRONT_IMAGE = "certificate_front_image";
        public static final String CERTIFICATE_BACK_IMAGE = "certificate_back_image";
        public static final String BANK_CARD_IMAGE = "bank_card_image";
        public static final String MOBILE = "mobile";

        // 获取新闻列表
        public static final String CID = "cid"; // 分类id
        public static final String ORDER = "order"; // 排序方式
        public static final String NEWS_PAGE = "page"; // 页码
        public static final String NEWS_PAGESIZE = "pagesize"; // 每页显示条数

        // 文交所id
        public static final String WJSID = "wjsid";

        // 获取下级用户列表
        public static final String UID = "uid"; // 用户id
        public static final String FANS_LEVEL = "level"; // 粉丝等级
        public static final String FANS_PAGESIZE = "pagesize"; // 粉丝列表每页条数
        public static final String FANS_PAGENUM = "pagenum"; // 粉丝列表页码

        public static final String AVATAR = "file"; // 用户头像


//        public static final String USERNAME = "";
//        public static final String USERNAME = "";
    }

    public class SPKey {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String FIRST_RUN = "first_run";
    }

}
