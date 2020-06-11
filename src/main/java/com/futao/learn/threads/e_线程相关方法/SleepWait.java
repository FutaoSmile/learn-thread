package com.futao.learn.threads.e_线程相关方法;

/**
 * @author 喜欢天文的pony站长
 * Created on 2020/6/11.
 */
public class SleepWait implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("currentThread: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        SleepWait sleepWait = new SleepWait();


        new Thread(sleepWait).start();
        new Thread(sleepWait).start();
    }
}
