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
    private String url;
    private RobotCalculator robotCalculator;
    Logger logger = LoggerFactory.getLogger(this.getClass());//这个class待定


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

        RobotMessage robotMessage = requestToRobotMessage(request);
        try{
            String reponse = HttpsUtils.postJson(this.url, robotMessage.toString());
            if (StringUtils.isBlank(reponse)){
                logger.error("钉钉消息发送失败！");
            }

            RobotResponse robotResponse = JSON.parseObject(reponse, new TypeReference<RobotResponse>() {});
            if (robotResponse.OK()){
                successed = true;
            }else{
                logger.error("钉钉消息发送失败, 返回结果为：{}", reponse);
            }
        }catch (Exception e){
            logger.error("钉钉消息发送失败：{}", e);
        }

        return successed;
    }

    private RobotMessage requestToRobotMessage(Request request){
        return new RobotMessage(request.getMsg(), request.getReceivers(), request.isAtAll());
    }
}
