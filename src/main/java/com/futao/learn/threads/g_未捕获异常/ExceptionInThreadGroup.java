package com.futao.learn.threads.g_未捕获异常;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * @date 2020/6/20
 */
@Slf4j
public class ExceptionInThreadGroup implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException("线程任务发生了异常");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("只知道抛出异常的线程组...") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                super.uncaughtException(t, e);
                log.error("线程组内捕获到线程[{},{}]异常", t.getId(), t.getName(), e);
            }
        };
        ExceptionInThreadGroup exceptionInThreadGroup = new ExceptionInThreadGroup();

        new Thread(threadGroup, exceptionInThreadGroup, "线程1").start();
        TimeUnit.MILLISECONDS.sleep(300L);

        //优先获取绑定在thread对象上的异常处理器
        Thread thread = new Thread(threadGroup, exceptionInThreadGroup, "线程2");
        thread.setUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(300L);

        new Thread(threadGroup, exceptionInThreadGroup, "线程3").start();
    }
}
