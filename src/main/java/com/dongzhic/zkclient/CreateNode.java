package com.dongzhic.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 创建节点
 * @Author dongzhic
 * @Date 2021/8/5 18:06
 */
public class CreateNode {

    public static void main(String[] args) {

        /**
         * 1.创建zkClient实例
         *  serverstring：服务器连接地址
         *  zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建");

        /**
         * 创建节点
         *  createParents ：是否要创建父节点，如果值为true，那么就回去递归创建节点
         */
        zkClient.createPersistent("/zkClient/c1", true);
        System.out.println("节点创建完成");

    }
}
