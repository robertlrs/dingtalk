package cn.idongjia.dingtalk.exception;

import cn.idongjia.dingtalk.common.DingtalkException;

public class IllegalArgException extends DingtalkException{

    private static final long serialVersionUID = 1L;

    public IllegalArgException(String message) {
        super(message);
    }

    public IllegalArgException(String name, Object value) {
        this(name, value, null);
    }

    public IllegalArgException(String name, Object value, String message) {
        super("Invalid arg " + value + " for configuration " + name + (message == null ? "" : ": " + message));
    }
}
