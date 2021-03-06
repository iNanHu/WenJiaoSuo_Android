package com.inanhu.wenjiaosuo.util;

/**
 * Created by Jason on 2016/6/30.
 */
public class URLUtil {

    /**
     * APP服务器地址
     */
    public static final String APP_SERVER = "http://wmyzt.applinzi.com";
    /**
     * API地址
     */
    public static final String API_SERVER = APP_SERVER + "/api";
    /**
     * APP分享注册地址
     */
    public static final String SHARE_REGISTER = APP_SERVER + "/register.html?invite=";
    /**
     * 邮币卡之家
     */
    public static final String YOUBICARD = "http://www.youbicard.com";
    /**
     * 邮币卡之家获取行情数据接口
     */
    public static final String EQUITY_FROM_YOUBICARD = YOUBICARD + "/plus/data/excList.php?action=zhongshuju";
    /**
     * 邮币卡之家获取公告数据接口
     */
    //TODO 目前只用只抓取第一页公告
    public static final String GONGGAO_FROM_YOUBICARD = YOUBICARD + "/member/getAgent2.php?wjsname=all&page=1";
    /**
     * 邮币卡之家获取文交所Logo接口
     *
     * @param logo
     * @return
     */
    public static String getWJSLogoUrl(String logo) {
        return YOUBICARD + logo;
    }

    /**
     * 用户相关接口
     */
    public static final class UserApi {
        public static final String USER = API_SERVER + "/user";
        public static final String REGISTER = USER + "/register";
        public static final String LOGIN = USER + "/login";
        public static final String LOGOUT = USER + "/logout";
        public static final String COMPLETE = USER + "/complete";
        public static final String INFO = USER + "/info";
        public static final String APPLY_WJS = USER + "/apply_wjs";
        public static final String RESET_PASS = USER + "/reset_pass";
        public static final String GETQR = USER + "/getqr";
        public static final String CHANGE_AVATAR = USER + "/change_avatar";
        public static final String GET_APPLY_STATUS = USER + "/get_apply_status";
        public static final String GET_FAN_LIST = USER + "/get_fan_list";
        public static final String GET_APPLY_STATUS_BYUID = USER +"/get_apply_status_byuid";
        public static final String GET_POSTER = USER + "/combine?invite=";
        public static final String CHECK_MOBILE = USER + "/check_mobile";
    }

    /**
     * 新闻接口
     */
    public static final class NewsApi {
        public static final String NEWS = API_SERVER + "/news";
        public static final String GETLIST = NEWS + "/getlist";
        public static final String DETAIL = NEWS + "/detail";
        public static final String GETCATEGORY = NEWS + "/getcategory";
        public static final String GETBANNER = NEWS + "/getbanner";
    }

    /**
     * 共用接口
     */
    public static final class CommonApi {
        public static final String COMMON = API_SERVER + "/common";
        public static final String UPFILE = COMMON + "/upfile";
    }


    /**
     * 文交所接口
     */
    public static final class WJSApi {
        public static final String WJS = API_SERVER + "/wjs";
        public static final String GETLIST = WJS + "/getlist";
    }
}
