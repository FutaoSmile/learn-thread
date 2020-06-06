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
            int num = 0;
            //会发现，虽然判断了线程是否被中断，但是发现线程一直都处于未被中断状态
            while (num <= 300000 && !Thread.currentThread().isInterrupted()) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的整数倍");
                }
                ++num;
                try {
                    //sleep()方法会响应中断，且会将线程的中断状态清除
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //传递中断
                }
            }
            System.out.println("线程执行完毕");
        });

        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }
}
