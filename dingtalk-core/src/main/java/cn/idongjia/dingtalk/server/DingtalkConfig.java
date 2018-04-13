package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.config.AbstractConfig;
import cn.idongjia.dingtalk.constant.ServerSettings;

import java.util.List;
import java.util.Map;

public class DingtalkConfig extends AbstractConfig{
    private String serverHost;
    private Integer serverPort;
    private Integer maxReqeustQueueSize;
    private Integer numWorkThread;
    private String robotUrls;
    private Integer robotMaxRequestOneMinute;


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
}
