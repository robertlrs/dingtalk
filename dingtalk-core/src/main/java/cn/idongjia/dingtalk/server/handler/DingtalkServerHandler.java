package cn.idongjia.dingtalk.server.handler;

import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.network.RequestChannel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class DingtalkServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    Logger logger = LoggerFactory.getLogger(DingtalkServerHandler.class);

    private  RequestChannel requestChannel;

    public DingtalkServerHandler(RequestChannel requestChannel){
        this.requestChannel = requestChannel;
    }

    @Deprecated
    public DingtalkServerHandler setRequestChannel(RequestChannel requestChannel){
        this.requestChannel = requestChannel;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        try{
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            if (fullHttpRequest.method() == HttpMethod.POST){
                Request request = formatRequest(fullHttpRequest);
                requestChannel.sendRequest(request);
                writeResponse("ok", response, HttpResponseStatus.OK);
            }else{
                writeResponse("钉钉消息发送只支持post方式", response, HttpResponseStatus.METHOD_NOT_ALLOWED);
            }
        }catch (Exception e){
            writeResponse(e.getMessage(), response, HttpResponseStatus.INTERNAL_SERVER_ERROR);
            logger.error("东家钉钉消息发送服务器异常：{}", e);
        }finally {
            channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }


    }

    private Request formatRequest(FullHttpRequest fullHttpRequest){

        ByteBuf jsonBuf = fullHttpRequest.content();
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        Request request = JSON.parseObject(jsonStr, new TypeReference<Request>() {});

        return request;
    }

    private void writeResponse(String msg, FullHttpResponse response, HttpResponseStatus status){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        response.setStatus(status);
        buffer.put(msg.getBytes());
        buffer.flip();
        response.content().writeBytes(buffer);
        buffer.clear();
    }
}
