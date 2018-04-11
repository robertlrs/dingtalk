package cn.idongjia.dingtalk.server.handler;

import cn.idongjia.dingtalk.network.Request;
import cn.idongjia.dingtalk.network.RequestChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.ByteBuffer;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class DingtalkServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private  RequestChannel requestChannel;

    public DingtalkServerHandler setRequestChannel(RequestChannel requestChannel){
        this.requestChannel = requestChannel;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("slkdjflksdf".getBytes());
        buffer.flip();
        response.content().writeBytes(buffer);
        buffer.clear();

        Request request = new Request();
        requestChannel.sendRequest(request);
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
