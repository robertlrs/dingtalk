package cn.idongjia.dingtalk.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO: 这个类待补充，目前只实现了info、debug、error三个接口
 */
public class Logging {
     Logger logger = LoggerFactory.getLogger(this.getClass());//这个class待定

     public void info(String msg){
          logger.info(msg);
     }

     public void info(String msg, Throwable e){
          logger.info(msg, e);
     }

     public void error(String msg){
          logger.error(msg);
     }

     public void error(String msg, Throwable e){
          logger.error(msg, e);
     }


     public void debug(String msg){
          logger.debug(msg);
     }

     public void debug(String msg, Throwable e){
          logger.debug(msg, e);
     }

     public void trace(String msg){
          logger.trace(msg);
     }

     public void trace(String msg, Throwable e){
          logger.trace(msg, e);
     }
}
