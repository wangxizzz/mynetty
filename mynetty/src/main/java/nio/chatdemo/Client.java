package nio.chatdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <Description>
 *
 * @author wangxi
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        // OP_CONNECT 创建客户端连接事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(8080));
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 表示连接上了
                if (key.isConnectable()) {
                    SocketChannel client = (SocketChannel)key.channel();
                    // 是否处于连接事件当中
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        // 连接建立好，发消息给服务端
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                        writeBuffer.flip();
                        client.write(writeBuffer);

                        // 接收键盘输入
                        ExecutorService service = Executors.newFixedThreadPool(2);
                        // 起一个新的线程获取用户输入
                        service.execute(() -> {
                            try {
                                while (true) {
                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                    String message = br.readLine();
                                    writeBuffer.clear();
                                    writeBuffer.put(message.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);
                                }
                            } catch (Exception e) {

                            }
                        });
                    }
                    client.register(selector, SelectionKey.OP_READ);

                } else if (key.isReadable()) {     // 接收server的消息
                    SocketChannel client = (SocketChannel)key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    // 把管道的信息读到buffer中
                    int read = client.read(readBuffer);
                    if (read > 0) {
                        String message = new String(readBuffer.array(), 0, read);
                        System.out.println(message);
                    }
                }
            }
        }
    }
}

