package com.futao.learn.threads.e_线程相关方法;

/**
 * 交替打印奇数和偶数
 *
 * @author 喜欢天文的pony站长
 * Created on 2020/6/9.
 */
public class AlternatePrintingOddEvenByRunnable implements Runnable {

    private static int toPrintNum = 0;
    private static final String ODD_THREAD_NAME = "奇数线程";
    private static final String EVEN_THREAD_NAME = "偶数线程";

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                String threadName = Thread.currentThread().getName();
                switch (threadName) {
                    case ODD_THREAD_NAME:
                        //当前是奇数线程
                        if (toPrintNum % 2 == 1) {
                            //当前是奇数->进行打印
                            System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                        } else {
                            //当前是偶数，唤醒偶数线程，交给偶数线程打印
                            notify();
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                        break;
                    case EVEN_THREAD_NAME:
                        //当前是偶数线程
                        if (toPrintNum % 2 == 0) {
                            //当前是偶数->进行打印
                            System.out.println(Thread.currentThread().getName() + ":" + toPrintNum++);
                        } else {
                            //当前是奇数，唤醒奇数线程，交给奇数线程打印
                            notify();
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                        break;
                    default:
                        throw new RuntimeException("不支持的线程名称");
                }
            }
        }
    }

    public static void main(String[] args) {
        AlternatePrintingOddEvenByRunnable alternatePrintingOddEvenByRunnable = new AlternatePrintingOddEvenByRunnable();


        new Thread(alternatePrintingOddEvenByRunnable, AlternatePrintingOddEvenByRunnable.ODD_THREAD_NAME).start();
        new Thread(alternatePrintingOddEvenByRunnable, AlternatePrintingOddEvenByRunnable.EVEN_THREAD_NAME).start();
    }
}
