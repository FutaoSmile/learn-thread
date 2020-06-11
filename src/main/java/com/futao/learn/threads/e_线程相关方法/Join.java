package com.futao.learn.threads.e_线程相关方法;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/11.
 */
public class Join {
    private static final Logger LOGGER = LoggerFactory.getLogger(Join.class);

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            LOGGER.info("执行完毕");
        });
        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            LOGGER.info("执行完毕");
        });


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        LOGGER.info("线程1线程2都执行完毕之后，main线程执行完毕");
    }
}
