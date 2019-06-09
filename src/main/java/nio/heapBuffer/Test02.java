package nio.heapBuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <Description>
 * 利用FileChannel读写文件
 * @author wangxi
 */
public class Test02 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("README.md");
        FileOutputStream outputStream = new FileOutputStream("out.txt");
        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        while (true) {
            //byteBuffer.clear();   // 加上clear()，或者加上compact()来重置Buffer的三个属性。
            int read = inChannel.read(byteBuffer);  // 如果position==limit，那么无论流是否到达结尾，都返回0
            System.out.println("实际读的字节数" + read);
            if (-1 == read) {
                break;
            }
            // 状态翻转
            byteBuffer.flip();

            outChannel.write(byteBuffer);  // byteBuffer写入输出channel,但是byteBuffer的底层数组元素没有改变
            byteBuffer.compact();
        }
        inChannel.close();
        outChannel.close();
    }
}

