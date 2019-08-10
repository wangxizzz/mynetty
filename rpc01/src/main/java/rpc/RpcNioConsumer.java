package rpc;

import proxy.RpcProxyFactory;
import service.HelloService;

import java.util.concurrent.CountDownLatch;

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
        long start = System.currentTimeMillis();
        HelloService proxy = RpcProxyFactory.getMultiService(HelloService.class);
        CountDownLatch latch = new CountDownLatch(10000);
        // 相当于10000个客户端共用一个连接
        for (int i = 0; i < 10000; i++) {
            final int j = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String result = proxy.sayHello("world!");
                    latch.countDown();
                }
            };
            Thread t = new Thread(runnable);
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("所用时间为" + (end - start));
    }
}

