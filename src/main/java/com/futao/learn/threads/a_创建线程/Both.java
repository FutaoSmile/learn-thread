package com.futao.learn.threads.a_创建线程;

/**
 * @author futao
 * @date 2020/6/4
 */
public class Both {

    public static void main(String[] args) {
        new Thread(() -> {
            //通过lambda表达式创建Runnable子类对象
            System.out.println("来自实现Runnable接口的run()方法");
        }) {
            //Thread的匿名内部类，直接重写Thread父类的run()方法
            @Override
            public void run() {
                System.out.println("来自重写Thread类的run()方法");
            }
        }.start();
    }
}
