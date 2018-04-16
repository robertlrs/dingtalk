package cn.idongjia.dingtalk.constant;

public interface ServerSettings {
    public static String DingtalkUrlList = "dingtalk.url.list";
    public static String ServerHost = "server.host";
    public static String ServerPort = "server.port";
    public static String MaxReqeustQueueSize = "max.request.queue.size";
    public static String NumWorkThreads = "num.work.threads";   //DingtalkHandlerPool线程数量

    public static String RobotUrls = "robot.urls";
    public static String RobotMaxRequestOneMinute = "robot.max.requests.one.minute";

    public static String DingtalkGroupPrefix= "dingtalk.group.";
    public static String DingtalkGroupUrlSuffix = ".robot.urls";
    public static String DingtalkGroupAppSuffix = ".app.names";

}
