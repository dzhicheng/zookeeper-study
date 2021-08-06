package com.dongzhic.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 创建节点
 * @Author dongzhic
 * @Date 2021/8/5 18:06
 */
public class DeleteNode {

    public static void main(String[] args) {

        /**
         * 1.创建zkClient实例
         *  serverstring：服务器连接地址
         *  zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建");

        /**
         * 删除节点
         *  deleteRecursive 递归删除
         */
        String path = "/zkClient/c1";
        zkClient.createPersistent(path + "/c11");
        zkClient.deleteRecursive(path);
        System.out.println("递归删除完成");

    }
}
