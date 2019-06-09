package nio.heapBuffer;

import java.nio.ByteBuffer;

/**
 * <Description>
 * ByteBuffer的put原生类型和readOnlyBuffer
 *  readOnlyBuffer不能转化为读写buffer
 * @author wangxi
 */
public class Test03 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putInt(10);

        buffer.flip();
        System.out.println(buffer.getInt());

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

    }
}

