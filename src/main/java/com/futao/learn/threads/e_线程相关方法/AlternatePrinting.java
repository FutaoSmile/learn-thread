package com.futao.learn.threads.e_线程相关方法;

/**
 * 交替打印
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/9.
 */
public class AlternatePrinting implements Runnable {

    private static int toPrintObject = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ":" + ++toPrintObject);
                this.notify();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        AlternatePrinting alternatePrinting = new AlternatePrinting();
        new Thread(alternatePrinting).start();
        new Thread(alternatePrinting).start();
    }
}
