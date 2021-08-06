package com.dongzhic.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 创建会话
 * @Author dongzhic
 * @Date 2021/8/5 23:52
 */
public class CreateNode {

    public static void main(String[] args) throws Exception {

        String connectString = "127.0.0.1:2181";

        // 失败策略
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base") // 设置独立命名空间 /base，为实现不同zookeeper业务间的隔离
                .build();
        client.start();
        System.out.println("会话创建了");

        // 创建节点
        String path = "/curator/c1";
        String s = client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, "数据内容：init".getBytes());
        System.out.println("节点递归创建成功，该节点路径为：" + s);

    }
}
