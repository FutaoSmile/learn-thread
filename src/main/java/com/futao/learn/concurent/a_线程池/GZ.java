package com.futao.learn.concurent.a_线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author futao
 * @date 2020/8/3
 */
@Slf4j
public class GZ extends ThreadPoolExecutor {

    public GZ(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info("线程执行任务之前");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        log.info("线程执行任务之后");
    }

    @Override
    protected void terminated() {
        super.terminated();
        log.info("线程池终止");
    }

    public static void main(String[] args) {
        GZ gz = new GZ(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            gz.execute(() -> {
                log.info("run...");
            });
        }
        gz.shutdown();
    }
}
