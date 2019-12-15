package netty_demo.day02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output : " + msg);
        ctx.writeAndFlush("from client : " + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("来自Client");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

