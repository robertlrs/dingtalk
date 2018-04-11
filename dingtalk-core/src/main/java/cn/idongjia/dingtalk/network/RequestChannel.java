package cn.idongjia.dingtalk.network;

import cn.idongjia.dingtalk.common.log.Logging;
import cn.idongjia.dingtalk.statistics.DingtalkMetric;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RequestChannel extends Logging{

    //请求队列
    private ArrayBlockingQueue requestQueue;
    private Map<String, DingtalkMetric> dingtalkMetricMap;

    public RequestChannel(Integer queueSize){
        requestQueue = new ArrayBlockingQueue<Request>(queueSize);
        dingtalkMetricMap = new ConcurrentHashMap<>();
    }

    public static Request AllDone = new Request();

    public void sendRequest(Request request) throws InterruptedException{
        requestQueue.put(request);
    }

    public Request receiveRequest(Long timeout) throws InterruptedException{
        return (Request) requestQueue.poll(timeout, TimeUnit.MILLISECONDS);
    }

}
