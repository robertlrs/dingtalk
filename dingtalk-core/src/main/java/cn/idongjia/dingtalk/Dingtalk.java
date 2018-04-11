package cn.idongjia.dingtalk;

import cn.idongjia.dingtalk.common.utils.Utils;
import cn.idongjia.dingtalk.server.DingtalkServerStartable;
import cn.idongjia.dingtalk.utils.CommandLineUtils;
import joptsimple.OptionParser;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dingtalk {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineUtils.class);

    public static Properties getProsFromArgs(String [] args){
        Properties properties = null;
        OptionParser optionParser = new OptionParser(false);
        try {
            if (null == args || args.length == 0) {
                CommandLineUtils.printUsageAndDie(optionParser, String.format("USAGE: java [options] %s server.properties", Dingtalk.class.getName()));
            }

            properties = Utils.loadProps(args[0]);

        }catch (IOException e){
            logger.error("Dingtalk 服务启动失败：{}", e);
            System.exit(1);
        }

        return properties;
    }

    public static void main(String[] args){
        Properties serverProperties = getProsFromArgs(args);
        System.out.println(serverProperties);
        final DingtalkServerStartable dingtalkServerStartable = new DingtalkServerStartable(serverProperties);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("dingtalk-shutdown-hook") {
            @Override
            public void run() {
                dingtalkServerStartable.shutdown();
            }
        });

        dingtalkServerStartable.startup();
        dingtalkServerStartable.awaitShutdown();
    }
}
