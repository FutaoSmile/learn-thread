package com.futao.learn.threads.a_1;

/**
 * @author futao
 * @date 2020/6/4
 */
public class Both {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("来自实现Runnable接口的run()方法");
        }) {
            @Override
            public void run() {
                System.out.println("来自重写Thread类的run()方法");
            }
        }.start();
    }
}
