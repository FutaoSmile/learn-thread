package com.futao.learn.threads.c_如何停止线程;

/**
 * 中断线程-run()方法中有sleep()或者wait()方法
 *
 * @author futao
 * @date 2020/6/6
 */
public class StopThreadWithSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num <= 300) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的整数倍");
                }
                ++num;
            }
            try {
                //sleep()方法会响应中断，且响应中断的方式为抛出InterruptException异常--- sleep interrupted
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行完毕");
        });
        thread.start();
        Thread.sleep(200L);
        thread.interrupt();
    }
}
