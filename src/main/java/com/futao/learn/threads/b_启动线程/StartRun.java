package com.futao.learn.threads.b_启动线程;

/**
 * 对比Thread类的start()与run()方法
 *
 * @author futao
 * @date 2020/6/6
 */
public class StartRun {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        //调用普通方法
        thread.run();
        //启动线程 1.判断线程状态，2.加入线程组 3.调用native方法start0()
        thread.start();
        //不能两次调用，否则会报错
        thread.start();
    }
}
