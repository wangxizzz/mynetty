package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * <Description>
 *
 * @author wangxi
 */
public class Test01 {
    public static void main(String[] args) throws IOException {
        int[] ports = {8080, 8081, 8081, 8084, 8085};
        Selector selector = Selector.open();
        System.out.println(SelectorProvider.provider().getClass());
        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket socket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            socket.bind(address);


        }
    }
}

