package com.futao.learn.threads.g_未捕获异常;

import java.util.concurrent.TimeUnit;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/16.
 */
public class ExceptionInChildThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException("子线程发生了异常...");
    }

    public static void main(String[] args) throws InterruptedException {
        try {

            new Thread(new ExceptionInChildThread()).start();
            TimeUnit.MILLISECONDS.sleep(200L);
            new Thread(new ExceptionInChildThread()).start();
            TimeUnit.MILLISECONDS.sleep(200L);
            new Thread(new ExceptionInChildThread()).start();
            TimeUnit.MILLISECONDS.sleep(200L);
            new Thread(new ExceptionInChildThread()).start();
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (Exception e) {
            //无法捕获发生在其他线程中的异常
            System.out.println("捕获到了异常?");
        }

    }
}
