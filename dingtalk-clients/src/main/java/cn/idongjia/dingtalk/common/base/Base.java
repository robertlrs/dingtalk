package cn.idongjia.dingtalk.common.base;

import com.google.gson.Gson;

import java.io.Serializable;

public abstract class Base implements Serializable {

    protected static final Gson GSON = new Gson();

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
