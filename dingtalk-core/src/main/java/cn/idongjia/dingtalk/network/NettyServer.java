package cn.idongjia.dingtalk.network;

import cn.idongjia.dingtalk.server.DingtalkConfig;
import cn.idongjia.dingtalk.server.handler.DingtalkServerHandler;
import cn.idongjia.dingtalk.utils.CommandLineUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    // nio redis server thread, default set Runtime.getRuntime().availableProcessors() * 2
    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime()
            .availableProcessors());
    private DingtalkConfig dingtalkConfig;

    public NettyServer(DingtalkConfig dingtalkConfig){
        this.dingtalkConfig = dingtalkConfig;
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void startup(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                        sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                        sc.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                        sc.pipeline().addLast("defaultRedisServerHandler", new DingtalkServerHandler());
                    }
                });

        String host = dingtalkConfig.getServerHost();
        int port = dingtalkConfig.getServerPort();
        ChannelFuture channelFuture = bootstrap.bind(host, port);
        channelFuture.syncUninterruptibly();
        logger.info("dingtalk.nettyserver started, please visit {}:{}", host, port);
    }
}
