package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.config.AbstractConfig;
import cn.idongjia.dingtalk.constant.ServerSettings;

import java.util.List;
import java.util.Map;

public class DingtalkConfig extends AbstractConfig{
    private List<String> dingTalkUrlList;
    private String serverHost;
    private Integer serverPort;
    private Integer maxReqeustQueueSize;
    private Integer numWorkThread;


    public DingtalkConfig(Map<?, ?> originals) {
        super(originals);

        String dingTalkUrlStr = getString(ServerSettings.DingtalkUrlList);
        serverHost = getString(ServerSettings.ServerHost);
        serverPort = getInt(ServerSettings.ServerPort);
        maxReqeustQueueSize = getInt(ServerSettings.MaxReqeustQueueSize);
        numWorkThread = getInt(ServerSettings.NumWorkThreads);
        if (numWorkThread > 30){
            numWorkThread = 30;
        }
    }


    public List<String> getDingTalkUrlList() {
        return dingTalkUrlList;
    }

    public void setDingTalkUrlList(List<String> dingTalkUrlList) {
        this.dingTalkUrlList = dingTalkUrlList;
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
}
