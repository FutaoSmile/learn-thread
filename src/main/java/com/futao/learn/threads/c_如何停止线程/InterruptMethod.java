package com.futao.learn.threads.c_如何停止线程;

/**
 * 线程中断的相关方法
 *
 * @author futao
 * @date 2020/6/7
 */
public class InterruptMethod {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("线程任务执行中...");
            while (true) {
            }
        });

        // 启动线程
        thread.start();
        System.out.println(thread.isInterrupted());
        // 向线程发送中断信号
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println(thread.isInterrupted());
        System.out.println(Thread.interrupted());
        System.out.println(thread.isInterrupted());
        System.out.println(thread.interrupted());
        System.out.println(thread.isInterrupted());
    }
}
