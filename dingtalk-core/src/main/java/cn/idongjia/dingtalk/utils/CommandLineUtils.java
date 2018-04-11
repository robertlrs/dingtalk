package cn.idongjia.dingtalk.utils;

import joptsimple.OptionParser;

import java.io.IOException;


public class CommandLineUtils {

    /**
     * Print usage and exit
     */
    public static void printUsageAndDie(OptionParser parser, String message) throws IOException{
        System.err.println(message);
        parser.printHelpOn(System.err);
//        Util.Exit.exit(1, Some(message));
        System.exit(0); //TODO: 待优化
    }
}
