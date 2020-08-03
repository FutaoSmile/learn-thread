package com.futao.learn.threads.j_死锁;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用tryLock避免死锁
 * * 可能产生活锁问题：
 * ** 两个线程同时tryLock等待x时间后超时，再同时获取到锁，再同时超时。
 * ** 这样会导致两个线程一直在运行，但是每次都获取不到锁。
 * *** 解决方案是: 设置随机时间
 *
 * @author futao
 * @date 2020/7/31.
 */
@Slf4j
public class TryLockDeadLock {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                while (true) {
                    // 尝试获取锁1
                    if (lock1.tryLock(1L, TimeUnit.SECONDS)) {
                        log.info("成功获取锁1，尝试获取锁2");
                        TimeUnit.SECONDS.sleep(1L);
                        // 尝试获取锁2
                        if (lock2.tryLock(1L, TimeUnit.SECONDS)) {
                            // 获取到锁2
                            log.info("成功获取锁2，结束运行");
                            lock1.unlock();
                            lock2.unlock();
                            break;
                        } else {
                            log.info("无法获取到锁2，暂时放弃锁1");
                            lock1.unlock();
                            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    if (lock2.tryLock(1L, TimeUnit.SECONDS)) {
                        log.info("成功获取锁2，尝试获取锁1");
                        TimeUnit.SECONDS.sleep(1L);
                        if (lock1.tryLock(1L, TimeUnit.SECONDS)) {
                            log.info("成功获取锁1，结束运行");
                            lock1.unlock();
                            lock2.unlock();
                            break;
                        } else {
                            log.info("无法获取到锁1，暂时放弃锁2");
                            lock2.unlock();
                            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
