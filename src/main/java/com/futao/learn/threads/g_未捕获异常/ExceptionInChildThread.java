package com.futao.learn.threads.g_未捕获异常;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/16.
 */
@Slf4j
public class ExceptionInChildThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException("子线程发生了异常...");
    }

    /**
     * 模拟子线程发生异常
     *
     * @throws InterruptedException
     */
    private static void exceptionThread() throws InterruptedException {
        Thread thread1 = new Thread(new ExceptionInChildThread());
        //为指定线程设置特定的异常处理器
        thread1.setUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(new ExceptionInChildThread()).start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(new ExceptionInChildThread()).start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(new ExceptionInChildThread()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
    }

    public static void main(String[] args) throws InterruptedException {
        exceptionThread();
//        ExceptionInChildThread.catchInMain();
//        ExceptionInChildThread.catchByHandler();
    }

    private static void catchByHandler() throws InterruptedException {
        //设置线程未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        exceptionThread();
    }

    /**
     * 在主线程尝试通过try catch捕获异常
     */
    private static void catchInMain() {
        try {
            exceptionThread();
        } catch (Exception e) {
            //无法捕获发生在其他线程中的异常
            log.error("捕获到了异常?", e);
        }
    }


}
