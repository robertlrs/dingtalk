package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.log.Logging;

import java.util.Map;
import java.util.Properties;

public class DingtalkServerStartable extends Logging{

    private DingtalkServer dingtalkServer;

    public DingtalkServerStartable(Properties properties){
        DingtalkConfig dingtalkConfig = new DingtalkConfig((Map<?, ?>)properties);
        dingtalkServer = new DingtalkServer(dingtalkConfig);
    }

    public void startup(){
        dingtalkServer.startup();
    }

    public void shutdown(){
        dingtalkServer.shutdown();

    }

    public void awaitShutdown(){
        dingtalkServer.awaitShutdown();
    }
}
