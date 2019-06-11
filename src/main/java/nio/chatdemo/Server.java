package nio.chatdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <Description>
 *  基于NIO的simple chatting room
 * @author wangxi
 */
public class Server {
    // 保存客户端channel信息，key表示客户端channel唯一标记
    private static Map<String, SocketChannel> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        // OP_ACCEPT监听客户端事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);

                    String identifier = "【" + UUID.randomUUID().toString() + "】";
                    // 把客户端channel保存在map中
                    clientMap.put(identifier, client);
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    // 读取客户端发过来的消息
                    int read = client.read(buffer);
                    if (read > 0) {
                        buffer.flip();
                        Charset charset = Charset.forName("utf-8");
                        String messageContent = String.valueOf(charset.decode(buffer).array());
                        System.out.println(client + " : " + messageContent);
                        // 找到当前客户端SocketChannel的UUID
                        String sendKey = null;
                        for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                            if (client == entry.getValue()) {
                                sendKey = entry.getKey();
                                break;
                            }
                        }
                        // 群发消息messageContent
                        for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                            SocketChannel clientChannel = entry.getValue();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((sendKey + " : " + messageContent).getBytes());
                            writeBuffer.flip();
                            clientChannel.write(writeBuffer);
                        }
                    }
                }
            }
        }
    }
}

