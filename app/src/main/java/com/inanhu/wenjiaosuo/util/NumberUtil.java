package com.inanhu.wenjiaosuo.util;

/**
 * Created by iNanHu on 2016/7/8.
 */
public class NumberUtil {

    /**
     * 数字转换为以万为单位
     * @param number
     * @return
     */
    public static String numberToWan(String number) {
        int temp = Integer.parseInt(number);
        double temp1 = (double) temp / 10000;
        return String.format("%.2f", temp1) + "万";
    }

    /**
     * 数字转换为以亿为单位
     * @param number
     * @return
     */
    public static String numberToYi(String number) {
        double temp = Double.parseDouble(number);
        double temp1 = temp / 100000000;
        return String.format("%.2f", temp1) + "亿";
    }
}
