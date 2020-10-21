package com.futao.learn.lagou;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * @date 2020/10/19
 */
public class DeadLock {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (LOCK_1) {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK_2) {
                    System.out.println(Thread.currentThread().getName() + "成功获取到LOCK2");
                }
            }
        }).start();


        new Thread(() -> {
            synchronized (LOCK_2) {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK_1) {
                    System.out.println(Thread.currentThread().getName() + "成功获取到LOCK2");
                }
            }
        }).start();


//        for (long deadlockedThread : ManagementFactory.getThreadMXBean().findDeadlockedThreads()) {
//            System.out.println(deadlockedThread);
//        }
    }
}
