package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;
import cn.idongjia.dingtalk.exception.IllegalArgException;
import cn.idongjia.dingtalk.metric.ApplicationMetric;
import cn.idongjia.dingtalk.network.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotManager extends Base{
    private Robot[] robots;
    private Integer currentRobot = 0;

    public RobotManager(List<String> robotUrls, Integer maxRequestOneMinute){
        if (CollectionUtils.isEmpty(robotUrls)){
            throw new IllegalArgException("robotUrls", robotUrls, "RobotManager 机器人url列表为空!");
        }

        robots = new Robot[robotUrls.size()];
        for (int i=0; i<robotUrls.size(); i++){
            robots[i] = new Robot(robotUrls.get(i).trim(), maxRequestOneMinute);
        }

    }

    public boolean sendMessage(Request request){
        if (StringUtils.isBlank(request.getApplication())){
            return false;
        }

        Robot robot = select();
        return robot.sendMessage(request);
    }

    private Robot select(){
        int size = robots.length;
        currentRobot = (currentRobot + 1) % size;
        return robots[currentRobot];
    }

}
