package day03;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 客户端向服务器发送任何消息，都会触发此方法的执行
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel channel = ctx.channel();
        channels.forEach(ch -> {
            // 广播所有channel
            if (ch != channel) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息 ：" + msg + "\n");
            } else {
                ch.writeAndFlush("[自己] : " + msg + "\n");
            }
        });
    }

    // 建立好连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 广播channel发消息(自己不用收到)
        channels.writeAndFlush("[服务器]-：" + channel.remoteAddress() + "上线\n");
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[服务器-：]" + channel.remoteAddress() + "离开\n");
        // netty会在连接断掉自动调用remove()
//        channels.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 服务器端自己的输出
        System.out.println(channel.remoteAddress() + "上线0");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线0");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

