package com.futao.learn.concurent.a_线程池;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * @date 2020/8/3
 */
public class Shutdown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.isShutdown();
        executorService.isTerminated();
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println(executorService.awaitTermination(10L, TimeUnit.SECONDS));
    }
}
