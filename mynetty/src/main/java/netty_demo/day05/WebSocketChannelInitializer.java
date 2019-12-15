package netty_demo.day05;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <Description>
 *
 * @author wangxi
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        // 以块的方式写
        pipeline.addLast(new ChunkedWriteHandler());
        // 聚合Http的消息
        pipeline.addLast(new HttpObjectAggregator(8192));
        // 处理webSocket协议
        pipeline.addLast(new WebSocketServerProtocolHandler("/wsss"));
        pipeline.addLast(new TestWebSocketFrameHandler());
    }
}

