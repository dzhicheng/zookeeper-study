package com.dongzhic.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * 删除节点
 * @Author dongzhic
 * @Date 2021/8/5 15:37
 */
public class DeleteNode implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        /*
            建立会话
            客户端可以通过创建⼀个zk实例来连接zk服务器
            new Zookeeper(connectString,sesssionTimeOut,Wather)
                connectString: 连接地址：IP：端⼝
                sesssionTimeOut：会话超时时间：单位毫秒
                Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
        */

        zooKeeper = new ZooKeeper("127.0.0.1", 5000, new DeleteNode());
        System.out.println(zooKeeper);

        Thread.sleep(Integer.MAX_VALUE);
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
            // 删除节点
            try {
                deleteNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除节点：同步方式
     */
    private void deleteNodeSync() throws InterruptedException, KeeperException {
        /*
            zooKeeper.exists(path,watch) :判断节点是否存在
            zookeeper.delete(path,version) : 删除节点
        */
        String path = "/study-persistent/c1";
        Stat stat = zooKeeper.exists(path, false);
        System.out.println(stat == null ? "该节点不存在" : "节点存在");

        if (stat != null) {
            zooKeeper.delete(path, -1);
        }

        Stat stat2 = zooKeeper.exists(path, false);
        System.out.println(stat2 == null ? "该节点不存在" : "节点存在");
    }

}
