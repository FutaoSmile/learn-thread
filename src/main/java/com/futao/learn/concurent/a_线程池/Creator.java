package com.futao.learn.concurent.a_线程池;

import java.util.concurrent.*;

/**
 * @author futao
 * @date 2020/8/3.
 */
public class Creator {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    TimeUnit.MINUTES.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void fix() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.submit(() -> {
                try {
                    TimeUnit.HOURS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
