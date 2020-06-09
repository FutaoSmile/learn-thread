package com.futao.learn.threads.e_线程相关方法;

/**
 * 交替打印100内的数字
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/9.
 */
public class AlternatePrintingOddEvenByThreadBetter {

    private static int toPrintNum = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                synchronized (AlternatePrintingOddEvenByThreadBetter.class) {
                    if (toPrintNum % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                        AlternatePrintingOddEvenByThreadBetter.class.notify();
                        try {
                            AlternatePrintingOddEvenByThreadBetter.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }, "偶数线程").start();

        new Thread(() -> {
            while (true) {
                synchronized (AlternatePrintingOddEvenByThreadBetter.class) {
                    if (toPrintNum % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                        AlternatePrintingOddEvenByThreadBetter.class.notify();
                        try {
                            AlternatePrintingOddEvenByThreadBetter.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }, "奇数线程").start();
    }
}
