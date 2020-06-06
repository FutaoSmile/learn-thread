package com.futao.learn.threads.a_创建线程;

/**
 * 通过继承Thread类，重写run()方法实现的线程
 *
 * @author futao
 * @date 2020/6/4
 */
public class ByThread extends Thread {

    @Override
    public void run() {
        System.out.println("通过继承Thread实现的多线程:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new ByThread().start();
    }
}
