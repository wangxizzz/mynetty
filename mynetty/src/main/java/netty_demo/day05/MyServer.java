package netty_demo.day05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * <Description>
 *  基于WebSocket
 * @author wangxi
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        // 配置服务端NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    // 创建一个netty自带的日志handler。
                    .handler(new LoggingHandler((LogLevel.INFO)))
                    .childHandler(new WebSocketChannelInitializer());
            // 绑定端口，同步等待成功,会在这里一直等待
            ChannelFuture f = b.bind(new InetSocketAddress(8080)).sync();

            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

