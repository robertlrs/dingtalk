package cn.idongjia.dingtalk.server.handler;

import cn.idongjia.dingtalk.Dingtalk;
import cn.idongjia.dingtalk.common.log.Logging;
import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.network.RequestChannel;
import cn.idongjia.dingtalk.network.DingtalkSender;

import java.util.concurrent.CountDownLatch;

public class DingtalkRequestHandler extends Logging implements Runnable {

    private RequestChannel requestChannel;
    private DingtalkSender dingtalkSender;
    private CountDownLatch latch = new CountDownLatch(1);
    private Integer id;


    public DingtalkRequestHandler(Integer id, RequestChannel requestChannel, DingtalkSender dingtalkSender){
        this.id = id;
        this.requestChannel = requestChannel;
        this.dingtalkSender = dingtalkSender;
    }

    @Override
    public void run() {
        info("request-request-hander" + this.id + " started");
        while (true){
            Request request = null;

            while(request == null){
                try{
                    request = requestChannel.receiveRequest(300l);
                }catch (InterruptedException e){
                    error(String.format("request hander %d thread get request failed: {}", this.id), e);
                }
            }

            if (request.equals(RequestChannel.AllDone)){
                debug(String.format("Kafka request handler %d received shut down command", this.id));
                latch.countDown();
                return;
            }

            this.dingtalkSender.sendMessage(request);
        }
    }

    public void initiateShutdown(){
        try{
            requestChannel.sendRequest(RequestChannel.AllDone);
        }catch (InterruptedException e){
            error("ding talk hander thread shut failed：{}", e);
        }
    }

    public void awaitShutdown(){
        try {
            latch.await();
        }catch (InterruptedException e){
            error("ding talk hander thread shut failed：{}", e);
        }
    }

    public RequestChannel getRequestChannel() {
        return requestChannel;
    }

    public void setRequestChannel(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }
}
