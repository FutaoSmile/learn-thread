package com.futao.learn.threads.g_未捕获异常;

/**
 * @author futao
 * @date 2020/6/20
 */
public class MainExcep {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        throw new RuntimeException("--");
    }
}
