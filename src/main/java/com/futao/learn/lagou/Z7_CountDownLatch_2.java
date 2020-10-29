package com.futao.learn.lagou;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 让多个线程等待一会，再一起开始执行
 * 让线程池中的5个线程等待，都准备好之后再一起执行
 *
 * @author futao
 * @date 2020/10/29
 */
public class Z7_CountDownLatch_2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                try {
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(1L);
        System.out.println("等待线程准备好");
        countDownLatch.countDown();

        threadPool.shutdown();

    }
}
