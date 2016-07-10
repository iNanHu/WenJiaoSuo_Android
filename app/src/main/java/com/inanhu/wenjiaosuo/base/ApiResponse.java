package com.inanhu.wenjiaosuo.base;

/**
 * Created by iNanHu on 2016/7/10.
 */
public class ApiResponse {

    private String msg; // 返回码信息：success-成功，error-失败
    private String data; // 返回内容

    public ApiResponse(String msg, String data) {
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断返回结果是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return "success".equals(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
