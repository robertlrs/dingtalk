package cn.idongjia.dingtalk.metric;

import cn.idongjia.dingtalk.common.base.Base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对应用发送钉钉消息的情况统计
 */
public class ApplicationMetric extends Base{

    private String name;
    private AtomicInteger rejected = new AtomicInteger(0);  //发送消息被拒绝次数
    private AtomicInteger successed = new AtomicInteger(0); //发送消息成功次数

    public ApplicationMetric(String applicationName){
        this.name = applicationName;
    }

    public AtomicInteger getRejected() {
        return rejected;
    }

    public void setRejected(AtomicInteger rejected) {
        this.rejected = rejected;
    }

    public AtomicInteger getSuccessed() {
        return successed;
    }

    public void setSuccessed(AtomicInteger successed) {
        this.successed = successed;
    }

    public void addRejected(int count){
        rejected.addAndGet(count);
    }

    public void addRejected(){
        rejected.addAndGet(1);
    }

    public void addSuccessed(int count){
        successed.addAndGet(count);
    }

    public void addSuccessed(){
        successed.addAndGet(1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
