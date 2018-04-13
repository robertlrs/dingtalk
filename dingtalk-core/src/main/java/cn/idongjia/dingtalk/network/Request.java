package cn.idongjia.dingtalk.network;

import cn.idongjia.dingtalk.common.base.Base;

import java.util.List;

public class Request extends Base{
    private String application; //应用
    private String module;  //日志模块
    private String host;    //发送过来的客户端，暂时用不到
    private String msg;
    private String msgId;   //消息在es中的id
    private List<String> receivers;   //钉钉消息的at谁
    boolean isAtAll = false;

    //这个要保留
    public Request(){

    }


    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }
}
