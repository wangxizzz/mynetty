package nio.directBuffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <Description>
 *  演示Java中的共享内存
 * @author wangxi
 */
public class Test01 {
    public static void main(String[] args) throws IOException {
        //ByteBuffer buffer = ByteBuffer.allocateDirect(100);

        FileChannel fileChannel = (new RandomAccessFile("README.md", "rw")).getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 3);
        // 可以操作内存(ByteBuffer实质就是一块内存)，就可以改变文件
        buffer.put(0, (byte) 'a');
    }
}

