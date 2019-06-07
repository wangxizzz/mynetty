package day03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyChatClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    // childHandler()与handler()方法相同。只是在childHandler中有两个线程组，这里只有一个
                    .handler(new MyChatClientInitializer());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            // 获取客户端的channel
            Channel channel = b.connect("localhost", 8080).sync().channel();
            for (;;) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }
        }finally {
            group.shutdownGracefully();
        }
    }
}

