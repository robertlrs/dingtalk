package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;

public class RobotResponse extends Base {
    private Integer errcode;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean OK(){
        return this.errcode == 0 && this.errmsg.trim().equals("ok");
    }
}
