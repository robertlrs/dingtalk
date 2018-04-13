package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;

/**
 * 效率待优化
 */
public class RobotCalculator extends Base {
    private Node[] nodes;
    private int len;
    private Integer maxCount = 0;
    private Integer currentCount = 0;
    private Integer duration;   //时间周期

    public RobotCalculator(int len, int maxCount, int duration){
        this.len = len;
        this.maxCount = maxCount;;
        this.duration = duration;

        nodes = new Node[len];
        for (int i=0; i<len; i++){
            nodes[i] = new Node();
        }
    }

    public boolean put(int index, Long time){
        boolean flag = false;
        if (index > len){
            return flag;
        }

        synchronized (nodes){
            if (this.currentCount < this.maxCount){
                flag = true;
            }else{
                //index后面的数据更容易命中， 别从0开始访问
                for (int i=index; i<len; i++){
                    flag = calculate(i, time);
                    if (flag){
                        break;
                    }
                }

                if (!flag){
                    for (int i=0; i<index-1; i++){
                        flag = calculate(i, time);
                        if (flag){
                            break;
                        }
                    }
                }

            }

            if (flag){
                this.currentCount += 1;
                if (nodes[index].getTime() == time){
                    nodes[index].addCount(1);
                }else{
                    nodes[index].setTime(time);
                    nodes[index].setCount(1);
                }
            }
        }

        return flag;
    }

    private boolean calculate(int index, long time){
        boolean flag = false;
        if (nodes[index].notEmpty() && time - nodes[index].getTime() >= this.duration){
            this.currentCount -= nodes[index].getCount();
            nodes[index].setCount(0);

            if (this.currentCount < this.maxCount){
                flag = true;
            }
        }

        return flag;
    }
}
