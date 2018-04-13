package cn.idongjia.dingtalk.robot;

import cn.idongjia.dingtalk.common.base.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotMessage extends Base {
    private String msgtype = MessageType.TEXT.getValue();

    private Map<String, Object> text = new HashMap<>();
    private Map<String, Object> at = new HashMap<>();

    public RobotMessage(String content, List<String> atMobiles, boolean isAtAll) {
        this.text.put("content", content);
        this.at.put("atMobiles", atMobiles);
        this.at.put("isAtAll", isAtAll);
    }

    public static void main(String[] args) {
        List<String> mobiles = new ArrayList<>();
        mobiles.add("19283");
        mobiles.add("1212");
        RobotMessage message = new RobotMessage("hah", mobiles, false);

        System.out.println(message);
    }

    enum  MessageType{

        TEXT("text");
        private String value;
        private  MessageType(String value){
            this.value = value;
        }


        public static MessageType parse(String value) {
            MessageType[] messageTypes = values();
            int len = messageTypes.length;

            for(int i = 0; i < len; ++i) {
                if (messageTypes[i].value.equals(value)) {
                    return messageTypes[i];
                }
            }

            return null;
        }

        public String getValue() {
            return value;
        }
    }

}
