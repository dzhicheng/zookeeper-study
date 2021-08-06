package com.dongzhic.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 创建会话
 * @Author dongzhic
 * @Date 2021/8/5 23:52
 */
public class UpdateNode {

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

        String path = "/curator/c1";

        // 数据内容
        byte[] data = client.getData().forPath(path);
        System.out.println("获取到的节点数据内容: " + new String(data));

        // 状态信息, 版本信息默认0
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("获取到的节点状态信息：" + stat);

        // 更新节点内容, 版本信息改为1
        int version = client.setData().withVersion(stat.getVersion())
                .forPath(path, "修改内容1".getBytes())
                .getVersion();
        System.out.println("当前的最新版本是" + version);
        byte[] afterData = client.getData().forPath(path);
        System.out.println("获修改后的节点数据内容: " + new String(afterData));

        //BadVersionException,指定的版本已经变更/过期
        client.setData().withVersion(stat.getVersion())
                .forPath(path, "修改内容2".getBytes());
    }
}
