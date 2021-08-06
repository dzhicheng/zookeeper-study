package com.dongzhic.api;

import org.apache.zookeeper.*;

import java.util.List;

/**
 * 获取节点数据
 * @Author dongzhic
 * @Date 2021/8/5 15:37
 */
public class GetNodeData implements Watcher {

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

        zooKeeper = new ZooKeeper("127.0.0.1", 5000, new GetNodeData());
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

        try {
            /**
             * 当子节点列表发生改变时，服务器端会发出nodeChildrenChanged时间通知，
             * 此时重新获取子节点列表，同时注意：通知是一次性的，需要反复注册监听
             */
            if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                getChildren();
            }

            // 服务端向客户端发送事件通知类型：SyncConnected
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                // 获取节点数据
                getNodeData();
                // 获取子节点链表
                getChildren();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取某个节点内容
     */
    private static void getNodeData () throws InterruptedException, KeeperException {

        /**
         * path : 获取数据的路径 *
         * watch : 是否开启监听 *
         * stat : 节点状态信息 *
         *      null: 表示获取最新版本的数据 *
         * zk.getData(path, watch, stat);
         * */
        byte[] data = zooKeeper.getData("/study-persistent", false, null);
        System.out.println(new String(data));

    }

    /**
     * 获取某个节点的子节点列表方法
     */
    public static void getChildren () throws InterruptedException, KeeperException {
        /*
            path:路径
            watch:是否要启动监听，当⼦节点列表发⽣变化，会触发监听
            zooKeeper.getChildren(path, watch);
        */
        List<String> children =  zooKeeper.getChildren("/study-persistent", true);
        System.out.println(children);
    }

}
