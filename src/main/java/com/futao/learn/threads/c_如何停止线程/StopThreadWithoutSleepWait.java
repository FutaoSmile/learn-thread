package com.futao.learn.threads.c_如何停止线程;

/**
 * 正确停止线程---run()方法内没有sleep()或者wait()方法
 *
 * @author futao
 * @date 2020/6/6
 */
public class StopThreadWithoutSleepWait implements Runnable {

    @Override
    public void run() {
        unHandleInterrupt();
        handleInterrupt();
    }

    /**
     * 未处理中断
     */
    public void unHandleInterrupt() {
        int num = 0;
        while (num <= Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000倍数");
            }
            ++num;
        }
        System.out.println("任务执行完毕");
    }

    /**
     * 响应中断
     */
    public void handleInterrupt() {
        int num = 0;
        //加入线程未被中断的条件
        while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000倍数");
            }
            ++num;
        }
        System.out.println("任务执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithoutSleepWait());
        //启动线程
        thread.start();
        //增加子线程处于运行状态的可能性
        Thread.sleep(500L);
        //尝试中断子线程
        thread.interrupt();
    }
}