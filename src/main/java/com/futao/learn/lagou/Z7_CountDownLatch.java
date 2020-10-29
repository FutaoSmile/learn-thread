package com.futao.learn.lagou;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 等待线程执行完毕
 * 如：让主线程等待其他线程执行完毕
 *
 * @author futao
 * @date 2020/10/29
 */
public class Z7_CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Work work = new Work(countDownLatch);
        new Thread(work).start();
        new Thread(work).start();
        new Thread(work).start();
        new Thread(work).start();
        countDownLatch.await();
    }


    @Getter
    @Setter
    @AllArgsConstructor
    static class Work implements Runnable {

        private CountDownLatch countDownLatch;

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.SECONDS.sleep(3L);
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            countDownLatch.countDown();
        }
    }
}
