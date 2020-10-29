package com.futao.learn.lagou;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author futao
 * @date 2020/10/26
 */
public class Z3_FairLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);

        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了锁");
        } finally {
            lock.unlock();
        }
    }
}
