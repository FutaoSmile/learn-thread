package com.futao.learn.threads.j_死锁;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;


/**
 * 使用ThreadMxBean检测死锁
 *
 * @author futao
 * @date 2020/7/30.
 */
@Slf4j
public class ThreadMxBeanDemo {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (LOCK_1) {
                log.info("成功获取到LOCK_1");
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_2) {
                    log.info("成功获取到LOCK_2");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (LOCK_2) {
                log.info("成功获取到LOCK_2");
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_1) {
                    log.info("成功获取到LOCK_1");
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(2L);

        long[] deadlockedThreads = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            log.error("发现死锁啦...");
            for (long deadlockedThread : deadlockedThreads) {
                log.error("线程ID={}的线程处于死锁状态", deadlockedThread);
            }
        }
    }
}
