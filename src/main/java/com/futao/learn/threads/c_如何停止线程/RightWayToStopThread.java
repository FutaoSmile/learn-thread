package com.futao.learn.threads.c_如何停止线程;

/**
 * 正确停止线程的方式1-抛出中断
 * 优先在方法签名中抛出该异常
 *
 * @author futao
 * @date 2020/6/6
 */
public class RightWayToStopThread implements Runnable {

    @Override
    public void run() {
//        catchInMethodCaller();
        throwInMethodCaller();
    }

    private void catchInMethodCaller() {
        while (true) {
            System.out.println("running...");
            catchInMethod();
        }
    }

    /**
     * 正确的处理方式
     */
    private void throwInMethodCaller() {
        while (true) {
            System.out.println("running...");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("响应中断，跳出循环，停止线程");
                break;
            }
        }
    }

    /**
     * 业务方法应该将中断异常抛出，将异常传递给上层--传递中断
     *
     * @throws InterruptedException
     */
    private void throwInMethod() throws InterruptedException {
        System.out.println("业务执行中.....");
        Thread.sleep(2000L);
    }

    /**
     * 生吞异常-线程无法收到中断信息，无法被中断
     */
    public void catchInMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayToStopThread());
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }
}
