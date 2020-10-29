package com.futao.learn.lagou;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author futao
 * @date 2020/10/26
 */
public class Z4_ReadWriteLock {
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        // 写锁
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    }
}
