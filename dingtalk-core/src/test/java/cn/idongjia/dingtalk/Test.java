package cn.idongjia.dingtalk;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args){
        final long minDiff = 3*1000;
        long lastSendTime = System.currentTimeMillis() - 500;
        long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - lastSendTime;
        if (diffTime < minDiff){
            try{
                System.out.println(minDiff - diffTime);

                Thread.sleep(minDiff - diffTime);
            }catch (InterruptedException e){

            }
        }
    }
}
