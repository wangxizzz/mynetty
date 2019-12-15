package netty_demo.nio.directBuffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * <Description>
 *  nio的scatter与gather.
 *  此样例可以通过linux的nc localhost 8080进行连接
 * @author wangxi
 */
public class Test02 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8080);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 +4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int readByte = 0;
            while (readByte < messageLength) {
                long read = socketChannel.read(buffers);
                readByte += read;
                System.out.println("byteRead : " + readByte);
                Arrays.asList(buffers).stream().map(x -> "position : " + x.position() + "limit : " + x.limit())
                        .forEach(System.out::println);
            }

            // 再写回客户端
            Arrays.asList(buffers).stream().forEach(x -> {
                x.flip();
            });

            int writeByte = 0;
            while (writeByte < messageLength) {
                long write = socketChannel.write(buffers);
                writeByte += write;
            }
            Arrays.asList(buffers).stream().forEach(x -> {
                x.clear();
            });
            System.out.println("readByte : " + readByte + "writeByte : " + writeByte + "messageLength" + messageLength);
        }
    }
}

