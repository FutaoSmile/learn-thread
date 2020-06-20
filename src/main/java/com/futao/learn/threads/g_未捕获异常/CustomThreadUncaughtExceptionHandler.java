package com.futao.learn.threads.g_未捕获异常;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义线程未捕获异常处理器
 *
 * @author futao
 * @date 2020/6/17
 */
public class CustomThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomThreadUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("自定义的线程异常处理器 捕获到线程发生的异常，线程信息:[{}]", t.toString(), e);
    }
}
