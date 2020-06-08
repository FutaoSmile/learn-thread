package com.futao.learn.threads.e_线程相关方法;

/**
 * Notify()与notifyAll()
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/8.
 */
public class NotifyAll {


    private static final Object SOURCE = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            synchronized (SOURCE) {
                System.out.println("线程[" + Thread.currentThread().getName() + "]获取到了同步监视器");
                try {
                    SOURCE.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();


        Thread.sleep(100L);
        new Thread(() -> {
            synchronized (SOURCE) {
                //程序无法停止下来，原因有两点：
                //  1。前面两个子线程都是在用户线程main线程中创建出来的，所以默认继承了main线程的用户线程属性，
                //  2。因为前面两个线程都处于Waiting等待状态，而notify()方法只会随机唤醒其中一个线程，所以还有一个线程处于Waiting状态，所以程序无法停止。
//                SOURCE.notify();

                //将两个线程全部唤醒
                SOURCE.notifyAll();
            }
        }).start();
    }

}
