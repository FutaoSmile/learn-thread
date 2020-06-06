package com.futao.learn.threads.a_1;

/**
 * @author futao
 * @date 2020/6/4
 */
public class ByRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("通过实现接口Runnable实现的多线程:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new Thread(new ByRunnable()).start();
    }
}
