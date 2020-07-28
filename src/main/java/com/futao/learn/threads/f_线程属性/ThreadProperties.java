package com.futao.learn.threads.f_线程属性;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/16.
 */
public class ThreadProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadProperties.class);

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread childThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        //必须在start()之前设置线程的优先级
        //childThread.setDaemon(true);
        childThread.start();

        LOGGER.info("main线程的id:{}", mainThread.getId());
        LOGGER.info("子线程的id:{}", childThread.getId());

        LOGGER.info("main线程的名字:{}", mainThread.getName());
        LOGGER.info("子线程的名字(修改之前):{}", childThread.getName());
        childThread.setName("childThread-1");
        LOGGER.info("子线程的名字(修改之后):{}", childThread.getName());

        LOGGER.info("main线程是否是守护线程{}", mainThread.isDaemon());
        LOGGER.info("子线程线程是否是守护线程{}", childThread.isDaemon());

        //不能在线程运行过程中设置线程的优先级
        childThread.setDaemon(true);
        LOGGER.info("子线程线程是否是守护线程{}", childThread.isDaemon());

    }
}
