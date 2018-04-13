package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.utils.Utils;
import cn.idongjia.dingtalk.network.DingtalkSender;
import cn.idongjia.dingtalk.network.RequestChannel;
import cn.idongjia.dingtalk.server.handler.DingtalkRequestHandler;
import cn.idongjia.dingtalk.common.log.Logging;

import java.util.Arrays;
import java.util.List;

public class DingtalkRequestHanderPool extends Logging{
    private RequestChannel requestChannel;
    private DingtalkRequestHandler[] requestHandlers;
    private DingtalkSender dingtalkSender;

    public DingtalkRequestHanderPool(Integer numThreads, RequestChannel requestChannel, DingtalkSender dingtalkSender){
        this.requestChannel = requestChannel;
        this.dingtalkSender = dingtalkSender;

        requestHandlers = new DingtalkRequestHandler[numThreads];

        for (int i=0; i<numThreads; i++) {
            DingtalkRequestHandler requestHandler = new DingtalkRequestHandler(i, requestChannel, dingtalkSender);
            this.requestHandlers[i] = requestHandler;
            Utils.daemonThread("dingtalk-request-hander" + i, requestHandler).start();
        }
    }

    public void shutdown(){
        info("start shutdown dingtalk hand pool ...");

        //往队列发送服务关闭信息，这样可以在关闭服务之前，处理完请求队列中的所有请求
        Arrays.stream(requestHandlers).forEach(handler -> handler.initiateShutdown());
        Arrays.stream(requestHandlers).forEach(handler -> handler.awaitShutdown());

        info("shutdown dingtalk hand pool completely");
    }
}
