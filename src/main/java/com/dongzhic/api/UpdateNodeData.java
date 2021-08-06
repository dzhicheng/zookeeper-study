package com.dongzhic.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * 修改节点数据
 * @Author dongzhic
 * @Date 2021/8/5 15:37
 */
public class UpdateNodeData implements Watcher {

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

        zooKeeper = new ZooKeeper("127.0.0.1", 5000, new UpdateNodeData());
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
            // 跟新数据节点方法
            try {
                updateNodeDataSync ();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 更新数据节点内容：同步方式
     */
    private void updateNodeDataSync() throws InterruptedException, KeeperException {

        /*
            path:路径
            data:要修改的内容 byte[]
            version:为-1，表示对最新版本的数据进⾏修改
            zooKeeper.setData(path, data,version);
        */
        byte[] preData = zooKeeper.getData("/study-persistent", false, null);
        System.out.println("修改前的值：" + new String(preData));

        // 修改/study-persistent的数据，stat：状态信息对象
        Stat stat = zooKeeper.setData("/study-persistent", "客户端修改节点数据".getBytes(),-1);

        byte[] afterData = zooKeeper.getData("/study-persistent", false, null);
        System.out.println("修改后的值：" + new String(afterData));
    }

}
