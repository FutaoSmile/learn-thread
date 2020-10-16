package com.futao.learn.concurent.b_threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程安全的方式格式化时间
 *
 * @author futao
 * @date 2020/8/11
 */
public class Main {

    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        }
    };

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                System.out.println(simpleDateFormatThreadLocal.get().format(new Date(finalI * 1000L)));
            });
        }
        executorService.shutdown();
    }
}
