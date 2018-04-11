package cn.idongjia.dingtalk.common.config;

import cn.idongjia.dingtalk.common.DingtalkException;

public class ConfigException extends DingtalkException{
    private static final long serialVersionUID = 1L;

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String name, Object value) {
        this(name, value, null);
    }

    public ConfigException(String name, Object value, String message) {
        super("Invalid value " + value + " for configuration " + name + (message == null ? "" : ": " + message));
    }

}
