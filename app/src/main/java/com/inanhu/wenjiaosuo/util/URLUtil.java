package com.inanhu.wenjiaosuo.util;

/**
 * Created by Jason on 2016/6/30.
 */
public class URLUtil {

    /**
     * APP服务器地址
     */
    public static final String APP_SERVER = "http://wmyzt.applinzi.com/api";
    /**
     * 邮币卡之家
     */
    public static final String YOUBICARD = "http://www.youbicard.com";
    /**
     * 邮币卡之家获取行情数据接口
     */
    public static final String EQUITY_FROM_YOUBICARD = YOUBICARD + "/plus/data/excList.php?action=zhongshuju";

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
        public static final String USER = APP_SERVER + "/user";
        public static final String REGISTER = USER + "/register";
        public static final String LOGIN = USER + "/login";
        public static final String COMPLETE = USER + "/complete";
        public static final String INFO = USER + "/info";
        public static final String APPLY_WJS = USER + "/apply_wjs";
        public static final String RESET_PASS = USER + "/reset_pass";
        public static final String GETQR = USER + "/getqr";
        public static final String CHANGE_AVATAR = USER + "/change_avatar";
    }

    /**
     * 新闻接口
     */
    public static final class NewsApi {
        public static final String NEWS = APP_SERVER + "/news";
        public static final String GETLIST = NEWS + "/getlist";
        public static final String DETAIL = NEWS + "/detail";
        public static final String GETCATEGORY = NEWS + "/getcategory";
        public static final String GETBANNER = NEWS + "/getbanner";
    }

    /**
     * 共用接口
     */
    public static final class CommonApi{
        public static final String COMMON = APP_SERVER + "/common";
        public static final String UPFILE = COMMON + "/upfile";
    }


}
