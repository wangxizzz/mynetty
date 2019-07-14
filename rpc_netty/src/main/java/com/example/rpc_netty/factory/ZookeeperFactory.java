package com.example.rpc_netty.factory;

import constant.Constants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;

/**
 * <Description>
 *
 * @author wangxi
 */
public final class ZookeeperFactory {

    public static CuratorFramework client;

    private static String zkServerIps = "127.0.0.1:2181";

    private ZookeeperFactory(){}

    public static synchronized CuratorFramework create() {
        if (client == null) {
            // 每个1s 去重连一次，最大重连次数为3
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.builder() // 使用工厂类来建造客户端的实例对象
                    .connectString(zkServerIps)  // 放入zookeeper服务器ip
                    .sessionTimeoutMs(10000).retryPolicy(retryPolicy)  // 设定会话时间以及重连策略
                    .build();  // 建立连接通道
            // 启动Curator客户端
            client.start();
        }
        return client;
    }

    public static void main(String[] args) throws Exception{
        CuratorFramework curatorFramework = create();
        System.out.println(curatorFramework);
        boolean isZkCuratorStarted = curatorFramework.isStarted();
        System.out.println("当前客户端的状态：" + (isZkCuratorStarted ? "连接中..." : "已关闭..."));
        // 先创建一个netty节点
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/netty");
        InetAddress inetAddress = InetAddress.getLocalHost();
        // 选择创建临时节点模式(随着zookeeper回话关闭而消失)   ,创建一个SERVER_PATH下的 一个IP的节点
        // 把当前server节点注册到zookeeper中去。其实就是创建一个节点
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(Constants.SERVER_PATH + inetAddress.getHostAddress());
    }
}

