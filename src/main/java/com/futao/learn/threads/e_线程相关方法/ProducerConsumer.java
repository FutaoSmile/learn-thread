package com.futao.learn.threads.e_线程相关方法;

import java.util.LinkedList;

/**
 * @author futao
 * @date 2020/6/9
 */
public class ProducerConsumer {

    private static final LinkedList<Object> STOCK = new LinkedList<>();
    private static final Object SOURCE = new Object();

    class Producer implements Runnable {
        @Override
        public void run() {

        }
    }


    class Consumer implements Runnable {

        @Override
        public void run() {

        }
    }
}
