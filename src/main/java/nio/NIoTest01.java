package nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * <Description>
 *
 * @author wangxi
 */
public class NIoTest01 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int num = new SecureRandom().nextInt(20);
            buffer.put(num);
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}

