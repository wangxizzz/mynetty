package netty_demo.day02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new MyClientInitializer());

            ChannelFuture f = b.connect("localhost", 8080).sync();

            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}

