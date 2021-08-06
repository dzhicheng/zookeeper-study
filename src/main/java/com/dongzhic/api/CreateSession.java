package com.dongzhic.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 创建会话
 * @Author dongzhic
 * @Date 2021/8/5 15:37
 */
public class CreateSession implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        /*
            建立会话
            客户端可以通过创建⼀个zk实例来连接zk服务器
            new Zookeeper(connectString,sesssionTimeOut,Wather)
                connectString: 连接地址：IP：端⼝
                sesssionTimeOut：会话超时时间：单位毫秒
                Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
        */

        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1", 5000, new CreateSession());
        System.out.println(zooKeeper);

        // 计数工具类，CountDownLatch：不让main方法结束，让线程处于等待阻塞
        countDownLatch.await();

        System.out.println("客户端与服务端会话真正建立");
    }

    /**
     * 回调方法：处理来自服务器端的watch通知
     *  sesion建立后，zookeeper服务端会向客户端发送事件通知
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {

        // 服务端向客户端发送事件通知类型：SyncConnected
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            //解除主程序CountDownLatch的等待阻塞
            System.out.println("process方法执行。。。。");
            countDownLatch.countDown();
        }

    }
}
