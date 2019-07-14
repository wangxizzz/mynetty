package de_encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * <Description>
 *  编解码
 * @author wangxi
 */
public class Test01 {

    public static void main(String[] args) {
        File file = new File("D:\\ideaprojects\\spring\\mynetty\\src\\main\\java\\de_encode\\Test01.java");
        try (
            // FileInputStream打开的FileChannel只能读取
            FileChannel inChannel = new FileInputStream(file).getChannel();
            // FileOutputStream打开的FileChannel只能写入
            FileChannel outChannel = new FileOutputStream("a.txt").getChannel()) {

            // 将FileChannel的数据全部映射成ByteBuffer
            MappedByteBuffer mbb = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            // 使用UTF-8的字符集来创建编解码器
            Charset charset = Charset.forName("utf-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharsetEncoder encoder = charset.newEncoder();
            // 把mbb解码为charBuffer
            CharBuffer charBuffer = decoder.decode(mbb);
            // 再编码为ByteBuffer
            //ByteBuffer byteBuffer = encoder.encode(charBuffer);
            ByteBuffer byteBuffer = Charset.forName("iso-8859-1").encode(charBuffer); // 会出现乱码

            // 输出到文件
            outChannel.write(byteBuffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

