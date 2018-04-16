package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;
import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.utils.HttpsUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Robot extends Base{
    Logger logger = LoggerFactory.getLogger(this.getClass());//这个class待定

    private String url;
    private RobotCalculator robotCalculator;
    private long lastSendTime=0;

    public Robot(String url, Integer maxRequestOneMinute){
        this.url = url;
        this.robotCalculator = new RobotCalculator(60, maxRequestOneMinute, 60*1000);
    }

    public boolean sendMessage(Request request){
        boolean ret = false;
        long currentTime = System.currentTimeMillis();
        int seconds = (int) (currentTime/1000%60);
        if(robotCalculator.put(seconds, currentTime)){
            ret = this.doSendMessage(request);
        }

        return ret;
    }

    private boolean doSendMessage(Request request){
        boolean successed = false;
        final long minDiff = 1*1000;
        long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - this.lastSendTime;
        if (diffTime < minDiff){
            try{
                Thread.sleep(minDiff - diffTime);
            }catch (InterruptedException e){
                logger.error(String.format("机器人: %s 发送消息睡眠异常：{}", url), e);
            }
        }

        RobotMessage robotMessage = requestToRobotMessage(request);
        try{
            String reponse = HttpsUtils.postJson(this.url, robotMessage.toString());
            if (StringUtils.isBlank(reponse)){
                logger.error(String.format("机器人：%s 钉钉消息发送失败！", url));
            }

            RobotResponse robotResponse = JSON.parseObject(reponse, new TypeReference<RobotResponse>() {});
            if (robotResponse.OK()){
                successed = true;
            }else{
                logger.error(String.format("机器人：%s 钉钉消息发送失败, 返回结果为：{}", url), reponse);
            }
        }catch (Exception e){
            logger.error(String.format("机器人：%s 钉钉消息发送失败：{}", url), e);
        }

        return successed;
    }

    private RobotMessage requestToRobotMessage(Request request){
        return new RobotMessage(request.getMsg(), request.getReceivers(), request.getAtAll());
    }
}
