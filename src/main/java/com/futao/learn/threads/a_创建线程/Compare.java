package com.futao.learn.threads.a_创建线程;

/**
 * @author futao
 * @date 2020/6/6
 */
public class Compare {


    static class ByThread extends Thread {
        @Override
        public void run() {
            System.out.println("通过继承Thread类的方式");
        }
    }

    static class ByRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("通过实现Runnable接口的方式");
        }
    }

    public static void main(String[] args) {
        ByThread byThread = new ByThread();
        byThread.start();
        //!!线程无法重用
//        byThread.start();
        //必须重新实例化线程对象-带来了性能开销
        new ByThread().start();

        ByRunnable byRunnable = new ByRunnable();
        Thread thread = new Thread(byRunnable);
        thread.start();

        thread.start();
    }
}
