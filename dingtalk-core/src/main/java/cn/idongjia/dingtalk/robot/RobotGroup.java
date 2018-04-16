package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.exception.IllegalArgException;
import cn.idongjia.dingtalk.network.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 钉钉群组
 */
public class RobotGroup {
    private String name;
    private Set<String> applications;
    private Robot[] robots;
    private Integer currentRobot = 0;
    private boolean isCommon = false;   //是否是大群，共用的。

    public RobotGroup(List<String> applications, List<String> robotUrls, Integer maxRequestOneMinute, boolean isCommon){
        if (!isCommon && CollectionUtils.isEmpty(applications)){
            throw new IllegalArgException("applications", applications, "RobotGroup 应用列表列表为空!");
        }

        if (CollectionUtils.isEmpty(robotUrls)){
            throw new IllegalArgException("robotUrls", robotUrls, "RobotGroup 机器人url列表为空!");
        }

        if (!isCommon){
            this.applications = applications.stream().collect(Collectors.toSet());
        }
        robots = new Robot[robotUrls.size()];
        for (int i=0; i<robotUrls.size(); i++){
            robots[i] = new Robot(robotUrls.get(i).trim(), maxRequestOneMinute);
        }
    }

    public RobotGroup(List<String> applications, List<String> robotUrls, Integer maxRequestOneMinute){
        this(applications, robotUrls, maxRequestOneMinute, false);
    }

    public RobotGroup(List<String> robotUrls, Integer maxRequestOneMinute){
        this(null, robotUrls, maxRequestOneMinute, true);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getApplications() {
        return applications;
    }

    public void setApplications(Set<String> applications) {
        this.applications = applications;
    }

    public Robot[] getRobots() {
        return robots;
    }

    public void setRobots(Robot[] robots) {
        this.robots = robots;
    }

    public Integer getCurrentRobot() {
        return currentRobot;
    }

    public void setCurrentRobot(Integer currentRobot) {
        this.currentRobot = currentRobot;
    }

    public boolean contains(String application){
        return this.applications.contains(application);
    }
}
