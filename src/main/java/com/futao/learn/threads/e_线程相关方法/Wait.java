package com.futao.learn.threads.e_线程相关方法;

/**
 * wait()的作用，会释放同步监视器。
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/8.
 */
public class Wait implements Runnable {


    private static final Object SOURCE = new Object();

    @Override
    public void run() {
        synchronized (SOURCE) {
            System.out.println("线程[" + Thread.currentThread().getName() + "]获取到了锁");
            try {
                //当前线程立即释放锁，并进入无限期Waiting状态
                SOURCE.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println("线程[" + Thread.currentThread().getName() + "]重新获取到了锁并结束了运行");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Wait wait = new Wait();

        new Thread(wait).start();
        Thread.sleep(20L);
        new Thread(() -> {
            synchronized (SOURCE) {
                System.out.println("线程[" + Thread.currentThread().getName() + "]获取到了锁");
                SOURCE.notify();
                System.out.println("线程[" + Thread.currentThread().getName() + "]调用了notify()方法");
            }
        }).start();

    }

}
