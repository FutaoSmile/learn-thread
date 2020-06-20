package com.futao.learn.threads.g_未捕获异常;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * @date 2020/6/17
 */
@Slf4j
public class ExceptionInCurThread {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("在主线程抛出异常，在主线程捕获");
        } catch (RuntimeException e) {
            log.error("捕获到异常", e);
        }
    }
}
