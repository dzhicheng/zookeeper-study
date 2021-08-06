package com.dongzhic.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 *
 * @Author dongzhic
 * @Date 2021/8/5 18:06
 */
public class NodeAPI {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 1.创建zkClient实例
         *  serverstring：服务器连接地址
         *  zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建");

        // 判断节点是否存在
        String path = "/zkClient-Ep";
        boolean exit = zkClient.exists(path);

        if (!exit) {
            // 创建临时节点
            zkClient.createEphemeral(path, "123abc");
        }

        // 读取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);

        // 注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            /**
             * 当节点数据内容发生变化，执行回调方法
             * @param s path
             * @param o 当前变化后的节点内容
             * @throws Exception
             */
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + "该节点内容被更新，更新的内容：" + o);
            }

            /**
             * 当节点被删除时，会执行的回调方法
             * @param s path
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + "该节点被删除");
            }
        });

        // 更新节点内容
        zkClient.writeData(path, "456abc");
        TimeUnit.SECONDS.sleep(1);

        // 删除节点
        zkClient.deleteRecursive(path);
        TimeUnit.SECONDS.sleep(1);
    }
}
