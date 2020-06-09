package com.futao.learn.threads.e_线程相关方法;

/**
 * 交替打印100内的数字
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/9.
 */
public class AlternatePrintingOddEvenByThread {

    private static int toPrintNum = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                //存在废操作-> 可能一直是某个线程获取到锁，一直判断不成立
                synchronized (AlternatePrintingOddEvenByThread.class) {
                    if (toPrintNum % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                    }
                }
            }
        }, "偶数线程").start();

        new Thread(() -> {
            while (true) {
                synchronized (AlternatePrintingOddEvenByThread.class) {
                    if (toPrintNum % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                    }
                }
            }
        }, "奇数线程").start();
    }
}
