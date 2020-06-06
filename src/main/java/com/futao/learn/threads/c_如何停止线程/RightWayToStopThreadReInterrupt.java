package com.futao.learn.threads.c_如何停止线程;

/**
 * 正确停止线程的方式2
 * 恢复中断
 *
 * @author futao
 * @date 2020/6/6
 */
public class RightWayToStopThreadReInterrupt implements Runnable {
    @Override
    public void run() {
        while (true && !Thread.currentThread().isInterrupted()) {
            System.out.println("running...");
            throwInMethod();
        }
    }

    private void throwInMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.out.println("感知到中断请求。");
            System.out.println("重新设置中断信号");
            //尝试恢复中断
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayToStopThreadReInterrupt());
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }
}
