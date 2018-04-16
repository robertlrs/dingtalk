package cn.idongjia.dingtalk.network;

import cn.idongjia.dingtalk.common.log.Logging;
import cn.idongjia.dingtalk.metric.ApplicationMetric;
import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.robot.RobotManager;
import cn.idongjia.dingtalk.server.DingtalkConfig;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DingtalkSender extends Logging{
    private Map<String, ApplicationMetric> rejectedMap = new ConcurrentHashMap<String, ApplicationMetric>();
    private RobotManager robotManager;
    private DingtalkConfig dingtalkConfig;

    public DingtalkSender(DingtalkConfig dingtalkConfig){
        this.dingtalkConfig = dingtalkConfig;
        this.robotManager = new RobotManager(dingtalkConfig);
    }

    public boolean sendMessage(Request request){
        boolean success = false ;
        try{
            success = robotManager.sendMessage(request);
        }catch (Exception e){
            error("钉钉消息发送失败：{}", e);
        }

        if (!success){
            statRejectedMetrics(request.getApplication());   //注意别重发，可能网络延时导致的
        }

        return success;
    }

    private void statRejectedMetrics(String application){
        if (!rejectedMap.containsKey(application)){
            ApplicationMetric metric = new ApplicationMetric(application);
            rejectedMap.putIfAbsent(application, metric);     //防止别的进程也在写数据
        }

        rejectedMap.get(application).addRejected();
    }
}
