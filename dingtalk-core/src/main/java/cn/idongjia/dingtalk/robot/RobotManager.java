package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.Dingtalk;
import cn.idongjia.dingtalk.common.base.Base;
import cn.idongjia.dingtalk.constant.ServerSettings;
import cn.idongjia.dingtalk.exception.IllegalArgException;
import cn.idongjia.dingtalk.metric.ApplicationMetric;
import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.server.DingtalkConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class RobotManager extends Base{
    DingtalkConfig config;
    private List<RobotGroup> robotGroups = new ArrayList<>();
    private RobotGroup commonGroup;

    public RobotManager(DingtalkConfig dingtalkConfig){
        this.config = dingtalkConfig;
        Integer maxRequestOneMinute = config.getRobotMaxRequestOneMinute();

        //小的群组
        if (CollectionUtils.isNotEmpty(config.getDingtalkGroups())){
            this.robotGroups = config.getDingtalkGroups().stream().map(groupName -> {
                String groupAppKey = ServerSettings.DingtalkGroupPrefix + groupName + ServerSettings.DingtalkGroupAppSuffix;
                List<String> applications = Arrays.stream(config.getString(groupAppKey).split(","))
                        .map(app -> { return app.trim(); }).collect(toList());
                String groupUrlKey = ServerSettings.DingtalkGroupPrefix + groupName + ServerSettings.DingtalkGroupUrlSuffix;
                List<String> robotUrls = Arrays.stream(config.getString(groupUrlKey).split(","))
                        .map(url -> {return url.trim();}).collect(toList());

                return new RobotGroup(applications, robotUrls, maxRequestOneMinute);
            }).collect(toList());
        }

        List<String> commonRobotUrls = Arrays.stream(config.getRobotUrls().split(",")).collect(toList());
        commonGroup = new RobotGroup(commonRobotUrls, maxRequestOneMinute);
    }

    public boolean sendMessage(Request request){
        if (StringUtils.isBlank(request.getApplication())){
            return false;
        }

        RobotGroup robotGroup = select(request.getApplication());
        return robotGroup.sendMessage(request);
    }

    private RobotGroup select(String application){
        RobotGroup robotGroup = this.robotGroups.stream().filter(group -> {return group.contains(application);}).findFirst().orElse(null);

        if (null == robotGroup){
            robotGroup = commonGroup;
        }

        return robotGroup;
    }

}
