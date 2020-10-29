package com.futao.learn.lagou;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量，许可证
 *
 * @author futao
 * @date 2020/10/29
 */
public class Z6_限流_信号量 {
    public static void main(String[] args) {
        // 信号量，许可证，只有三个许可证，非公平
        //同时只允许三个线程访问资源
        Semaphore semaphore = new Semaphore(3);

        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        Work work = new Work(semaphore);
        for (int i = 0; i < 50; i++) {
            threadPool.execute(work);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Work implements Runnable {

        private Semaphore semaphore;

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "获取到许可证");
                TimeUnit.SECONDS.sleep(3L);
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
