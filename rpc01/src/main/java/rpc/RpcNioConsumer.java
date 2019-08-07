package rpc;

import proxy.RpcProxyFactory;
import service.HelloService;

/**
 * <Description>
 *
 * @author wangxi
 */
public class RpcNioConsumer {
    public static void main(String[] args) {
        multipartRpcNio();
    }

    /**
     * 多线程IO调用示例
     *
     */
    public static void multipartRpcNio() {
        HelloService proxy = RpcProxyFactory.getMultiService(HelloService.class);
        for (int i = 0; i < 100; i++) {
            final int j = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String result = proxy.sayHello("world!");
                }
            };
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}

