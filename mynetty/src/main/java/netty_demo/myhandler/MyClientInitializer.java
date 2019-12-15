package netty_demo.myhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <Description>
 *
 * @author wangxi
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast(new MyClientHandler());

    }
}

