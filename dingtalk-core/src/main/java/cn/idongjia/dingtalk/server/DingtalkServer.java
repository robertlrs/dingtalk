package cn.idongjia.dingtalk.server;

import cn.idongjia.dingtalk.common.log.Logging;
import cn.idongjia.dingtalk.network.NettyServer;
import cn.idongjia.dingtalk.network.RequestChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DingtalkServer extends Logging {

    private DingtalkConfig dingtalkConfig;
    private NettyServer nettyServer;
    private RequestChannel requestChannel;
    private DingtalkRequestHanderPool requestHanderPool;

    private CountDownLatch shutdownLatch = new CountDownLatch(1);
    private AtomicBoolean isShuttingDown = new AtomicBoolean(false);


    public DingtalkServer(DingtalkConfig dingtalkConfig){
        this.dingtalkConfig = dingtalkConfig;
    }

    public void shutdown(){
        info("dingtalk server closed normal, releasing threads...");

        if (shutdownLatch.getCount() > 0 && isShuttingDown.compareAndSet(false, true)){
            nettyServer.shutdown(); //先关闭nettyserver，不接收新的请求;
            requestHanderPool.shutdown();

            shutdownLatch.countDown();
        }

        info("dingtalk server closed completely");

    }

    public void startup(){
        info("dingtalk server is starting, please wait ...");
        this.requestChannel = new RequestChannel(dingtalkConfig.getMaxReqeustQueueSize());

        this.nettyServer = new NettyServer(dingtalkConfig, requestChannel);
        nettyServer.startup();

        Integer numHandlerThreads = dingtalkConfig.getNumWorkThread();
        this.requestHanderPool = new DingtalkRequestHanderPool(numHandlerThreads, requestChannel);

        info("dingtalk server is stared");

    }

    public void awaitShutdown(){
        try{
            this.shutdownLatch.await();
        }catch (InterruptedException e){
            error("ding talk server awaitShutdown failed : {}", e);
        }
    }
}
