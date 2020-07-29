package com.futao.learn.threads.i_底层原理;

import java.util.concurrent.CountDownLatch;

/**
 * volatile并不可以保证原子性
 *
 * @author futao
 * @date 2020/7/29
 */
public class VolatileDemo implements Runnable {

    volatile int i = 0;
    CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();
        new Thread(volatileDemo).start();
        new Thread(volatileDemo).start();
        volatileDemo.countDownLatch.await();
        System.out.println(volatileDemo.i);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            i++;
        }
        this.countDownLatch.countDown();
    }
}
