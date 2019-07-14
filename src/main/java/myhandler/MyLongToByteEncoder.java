package myhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <Description>
 * 编码器 。
 * @author wangxi
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {  // 泛型代表把Long数据编码成byte发送出去
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) {
        System.out.println("encode invoked");
        System.out.println(msg);
        // 把消息写到byteBuf，发送出去
        out.writeLong(msg);
    }
}

