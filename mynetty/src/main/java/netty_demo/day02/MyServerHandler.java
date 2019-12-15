package netty_demo.day02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println(msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // 出现异常，关闭ChannelHandlerContext
        ctx.close();
    }
}

