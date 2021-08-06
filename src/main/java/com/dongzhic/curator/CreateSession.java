package com.dongzhic.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 创建会话
 * @Author dongzhic
 * @Date 2021/8/5 23:52
 */
public class CreateSession {

    public static void main(String[] args) {

        String connectString = "127.0.0.1:2181";
        // 失败策略
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);

        // 1.不使用fluent编程风格
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println("会话被建立了");

        // 2.使用fluent编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base") // 设置独立命名空间 /base，为实现不同zookeeper业务间的隔离
                .build();
        client.start();
        System.out.println("会话2创建了");

    }
}
