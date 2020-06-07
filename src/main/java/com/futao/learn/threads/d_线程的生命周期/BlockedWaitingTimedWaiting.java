package com.futao.learn.threads.d_线程的生命周期;

/**
 * 演示Blocked，Waiting，TimedWaiting
 *
 * @author futao
 * @date 2020/6/7
 */
public class BlockedWaitingTimedWaiting implements Runnable {
    @Override
    public void run() {
        try {
            sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步方法
     *
     * @throws InterruptedException
     */
    public synchronized void sync() throws InterruptedException {
        Thread.sleep(1000L);
        wait();
    }

    public static void main(String[] args) throws InterruptedException {
        BlockedWaitingTimedWaiting blockedWaitingTimedWaiting = new BlockedWaitingTimedWaiting();

        Thread thread1 = new Thread(blockedWaitingTimedWaiting);
        Thread thread2 = new Thread(blockedWaitingTimedWaiting);

        thread1.start();
        //为了尽可能让thread1先执行，获取到同步方法的锁
        Thread.sleep(10L);
        thread2.start();

        Thread.sleep(500L);
        System.out.println("因为thread1处于sleep(time)状态，所以预期线程状态为<TimedWaiting>: " + thread1.getState());
        System.out.println("因为sync()方法的锁被thread1持有，所以thread2被阻塞，预期状态为<Blocked>: " + thread2.getState());


        //等待thread1的sleep(time)时间结束,进入wait()方法
        Thread.sleep(1000L);
        System.out.println("因为thread1的sleep(time)时间结束,进入wait()方法,所以预期thread1的线程状态为<Waiting>：" + thread1.getState());
    }
}
