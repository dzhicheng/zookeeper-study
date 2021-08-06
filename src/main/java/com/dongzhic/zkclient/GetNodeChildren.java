package com.dongzhic.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 获取节点
 * @Author dongzhic
 * @Date 2021/8/5 18:06
 */
public class GetNodeChildren {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 1.创建zkClient实例
         *  serverstring：服务器连接地址
         *  zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建");

        // 获取子节点列表
        List<String> children = zkClient.getChildren("/zkClient");
        System.out.println(children);

        /**
         * 注册监听事件
         *  客户端可以对一个不存在的节点进行子节点变更的监听
         *  只要该节点的子节点列表，或者该节点本身被创建或者删除，都会触发监听
          */
        zkClient.subscribeChildChanges("/zkClient-get", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                /**
                 * s:parentPath
                 * list:变化后子节点列表
                 */
                System.out.println(parentPath + "的子节点列表发生了变化，变化后的子节点列表为" + list);
            }
        });

        // 测试
        zkClient.createPersistent("/zkClient-get");
        TimeUnit.SECONDS.sleep(1);

        zkClient.createPersistent("/zkClient-get/c1");
        TimeUnit.SECONDS.sleep(1);

    }
}
