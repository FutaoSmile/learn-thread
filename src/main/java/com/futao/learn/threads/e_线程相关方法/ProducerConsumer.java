package com.futao.learn.threads.e_线程相关方法;

import java.util.LinkedList;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/9.
 */
public class ProducerConsumer {

    // TODO: 2020/6/9 重写，没有实现

    static class Stock {

        /**
         * 最大库存
         */
        private static final int MAX_STOCK_SIZE = 10;

        /**
         * 库存资源
         */
        private static final LinkedList<Object> STOCK = new LinkedList<>();

        /**
         * 生产产品，增加库存
         *
         * @throws InterruptedException
         */
        public synchronized void put() throws InterruptedException {
            while (STOCK.size() < MAX_STOCK_SIZE) {
                //如果库存还没有满,则生产
                Thread.sleep(20L);
                STOCK.add(new Object());
                System.out.println(Thread.currentThread().getName() + "：入库，当前库存" + STOCK.size());
            }
            notify();
            wait();
        }


        /**
         * 消费产品，减库存
         *
         * @throws InterruptedException
         */
        public synchronized void take() throws InterruptedException {
            while (STOCK.size() > 0) {
                //有库存，可以消费
                Thread.sleep(10L);
                STOCK.pop();
                System.out.println(Thread.currentThread().getName() + "：出库，当前库存" + STOCK.size());
            }
            notify();
            wait();
        }
    }


    public static void main(String[] args) {
        Stock stock = new Stock();

        new Thread(() -> {
            try {
                stock.put();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                stock.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
