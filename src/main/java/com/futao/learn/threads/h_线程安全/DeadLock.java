package com.futao.learn.threads.h_线程安全;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 死锁演示
 *
 * @author futao
 * @date 2020/7/28
 */
@Slf4j
public class DeadLock {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();


    public static void main(String[] args) {
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
    }
}
