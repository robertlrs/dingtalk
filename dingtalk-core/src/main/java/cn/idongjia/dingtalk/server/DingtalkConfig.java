package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.config.AbstractConfig;
import cn.idongjia.dingtalk.constant.ServerSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class DingtalkConfig extends AbstractConfig{
    private String serverHost;
    private Integer serverPort;
    private Integer maxReqeustQueueSize;
    private Integer numWorkThread;
    private String robotUrls;
    private Integer robotMaxRequestOneMinute;
    private List<String> dingtalkGroups;


    public DingtalkConfig(Map<?, ?> originals) {
        super(originals);

        serverHost = getString(ServerSettings.ServerHost);
        serverPort = getInt(ServerSettings.ServerPort);
        maxReqeustQueueSize = getInt(ServerSettings.MaxReqeustQueueSize);
        numWorkThread = getInt(ServerSettings.NumWorkThreads);
        if (numWorkThread > 30){
            numWorkThread = 30;
        }

        robotUrls = getString(ServerSettings.RobotUrls);
        this.robotMaxRequestOneMinute = getInt(ServerSettings.RobotMaxRequestOneMinute);

        this.dingtalkGroups = getKeys().stream().filter(key -> { return key.startsWith(ServerSettings.DingtalkGroupPrefix); })
                .map(key -> { return key.substring(ServerSettings.DingtalkGroupPrefix.length()).split("\\.")[0]; })
                .collect(toSet()).stream().collect(toList());
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getMaxReqeustQueueSize() {
        return maxReqeustQueueSize;
    }

    public void setMaxReqeustQueueSize(Integer maxReqeustQueueSize) {
        this.maxReqeustQueueSize = maxReqeustQueueSize;
    }

    public Integer getNumWorkThread() {
        return numWorkThread;
    }

    public void setNumWorkThread(Integer numWorkThread) {
        this.numWorkThread = numWorkThread;
    }

    public String getRobotUrls() {
        return robotUrls;
    }

    public void setRobotUrls(String robotUrls) {
        this.robotUrls = robotUrls;
    }

    public Integer getRobotMaxRequestOneMinute() {
        return robotMaxRequestOneMinute;
    }

    public void setRobotMaxRequestOneMinute(Integer robotMaxRequestOneMinute) {
        this.robotMaxRequestOneMinute = robotMaxRequestOneMinute;
    }

    public List<String> getDingtalkGroups() {
        return dingtalkGroups;
    }

    public void setDingtalkGroups(List<String> dingtalkGroups) {
        this.dingtalkGroups = dingtalkGroups;
    }
}
