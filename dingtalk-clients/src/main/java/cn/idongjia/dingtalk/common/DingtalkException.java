package cn.idongjia.dingtalk.common;

public class DingtalkException extends RuntimeException{
    private final static long serialVersionUID = 1L;

    public DingtalkException(String message, Throwable cause) {
        super(message, cause);
    }

    public DingtalkException(String message) {
        super(message);
    }

    public DingtalkException(Throwable cause) {
        super(cause);
    }

    public DingtalkException() {
        super();
    }

}
