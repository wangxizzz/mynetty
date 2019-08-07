package rpc;

import common.BeanContainer;
import niomultipart.RpcNioMultiServer;
import service.HelloService;
import service.HelloServiceImpl;

import java.io.IOException;

/**
 * <Description>
 *服务端服务发布：
 * @author wangxi
 */
public class RpcNioProvider {
    public static void main(String[] args) throws IOException {
        // 将服务放进bean容器
        HelloService helloService = new HelloServiceImpl();
        BeanContainer.addBean(HelloService.class, helloService);
        // 启动NIO服务端
        startMultiRpcNioServer();
    }

    public static void startMultiRpcNioServer() {
        Runnable r = () -> {
            try {
                RpcNioMultiServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}

