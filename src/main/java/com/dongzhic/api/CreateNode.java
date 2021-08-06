package com.dongzhic.api;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * 创建节点
 * @Author dongzhic
 * @Date 2021/8/5 15:37
 */
public class CreateNode implements Watcher {

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

        zooKeeper = new ZooKeeper("127.0.0.1", 5000, new CreateNode());
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
            // 创建节点
            try {
                createNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 创建节点:同步的方式
     */
    private static void createNodeSync () throws InterruptedException, KeeperException {
        /**
         * path ：节点创建的路径
         * data[] ：节点创建要保存的数据，是个byte类型的
         * acl ：节点创建的权限信息(4种类型)
         *       ANYONE_ID_UNSAFE : 表示任何⼈
         *       AUTH_IDS ：此ID仅可⽤于设置ACL。它将被客户机验证的ID替换。
         *       OPEN_ACL_UNSAFE：这是⼀个完全开放的ACL(常⽤)--> world:anyone
         *       CREATOR_ALL_ACL ：此ACL授予创建者身份验证ID的所有权限
         * createMode ：创建节点的类型(4种类型)
         *       PERSISTENT：持久节点
         *       PERSISTENT_SEQUENTIAL：持久顺序节点
         *       EPHEMERAL：临时节点
         *       EPHEMERAL_SEQUENTIAL：临时顺序节点
         * String node = zookeeper.create(path,data,acl,createMode);
         **/

        // 持久节点
        String note_persistent = zooKeeper.create("/study-persistent", "持久节点内容".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

        // 临时节点
        String note_ephemeral = zooKeeper.create("/study-ephemeral", "临时节点内容".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

        // 持久顺序节点
        String note_persistent_ephemeral = zooKeeper.create("/study-persistent-ephemeral", "持久顺序节点内容".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println("创建持久节点" + note_persistent);
        System.out.println("创建临时节点" + note_ephemeral);
        System.out.println("创建持久顺序节点" + note_persistent_ephemeral);
    }


}
