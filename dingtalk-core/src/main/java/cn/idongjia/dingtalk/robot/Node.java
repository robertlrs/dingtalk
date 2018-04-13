package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;

class Node extends Base{
    private Long time;
    private int count;

    public Node(){
        this.time = 0l;
        this.count = 0;
    }

    public Node(long time, int count){
        this.time = time;
        this.count = count;
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean empty(){
        return this.count == 0;
    }

    public boolean notEmpty(){
        return !this.empty();
    }

    public void addCount(int count){
        this.count += count;
    }
}