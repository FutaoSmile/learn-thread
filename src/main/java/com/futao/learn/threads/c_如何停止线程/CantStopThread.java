package com.futao.learn.threads.c_如何停止线程;

/**
 * 3. 无法停止的线程
 *
 * @author futao
 * @date 2020/6/6
 */
public class CantStopThread {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 1;
            //会发现，虽然判断了线程是否被中断，但是发现线程一直都处于未被中断状态
            while (num <= 1000 && !Thread.currentThread().isInterrupted()) {
                if (num % 2 == 0) {
                    System.out.println(num + "是2的整数倍");
                }
                ++num;
                try {
                    //sleep()方法会响应中断，且会将线程的中断状态清除
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //传递中断
                }
            }
            System.out.println("线程执行完毕");
        });

        //启动线程
        thread.start();
        //主线程休眠500毫秒
        Thread.sleep(500L);
        //中断线程
        thread.interrupt();
    }
}
