package com.inanhu.wenjiaosuo.base;

import java.util.Hashtable;

/**
 * 保存全局变量
 * <p/>
 * Created by Jason on 2016/7/6.
 */
public final class GlobalValue {

    private Hashtable<String, Object> hashGlobal = new Hashtable<>();
    private static GlobalValue instance;

    public static GlobalValue getInstance() {
        if (instance == null) {
            instance = new GlobalValue();
        }
        return instance;
    }

    /**
     * 保存数据为全局
     *
     * @param key
     * @param data
     * @return
     */
    public void saveGlobal(String key, Object data) {
        if (hashGlobal.containsKey(key)) {
            hashGlobal.remove(key);
        }
        if (data != null) {
            hashGlobal.put(key, data);
        }
    }

    public void clearGlobal() {
        hashGlobal.clear();
    }

    /**
     * 读取全局数据
     *
     * @param key
     * @return
     */
    public Object getGlobal(String key) {
        return getGlobal(key, "");
    }

    public Object getGlobal(String key, Object defaultValue) {
        if (hashGlobal.containsKey(key)) {
            return hashGlobal.get(key);
        } else {
            return defaultValue;
        }
    }
}
