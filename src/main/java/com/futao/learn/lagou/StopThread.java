package com.futao.learn.lagou;

import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;

import java.util.concurrent.TimeUnit;

/**
 * 停止线程
 * 1. thread.interrupt();向线程发送终止信号，提示线程应该结束了
 * 2. Thread.currentThread().isInterrupted() 判断线程是否被标记为停止，并且会清除线程的中断状态。
 * 3. thread.isInterrupted() 判断thread是否被标记为停止，并且不会清除线程的中断状态。
 * <p>
 * 我们不应该强制停止线程，而是应该通知线程应该要停止了，具体的要不要停止线程，何时停止线程，交给线程程序的编写者来处理/响应，他们才知道应该在何时停止线程，并完成对资源的回收。
 * 响应中断:
 * 如sleep()/wait()等会让线程进入阻塞状态的方法都会响应中断，在这些方法的执行过程中线程收到了中断请求，会抛出InterruptedException，并且清除线程的中断信号
 * 中断处理：
 *  1. 在方法签名中向上抛出中断异常
 *  2. 再次中断，手动将线程的中断状态设置为true
 *
 * @author futao
 * @date 2020/10/16
 */
public class StopThread implements A{
//    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(() -> {
//            int i = 0;
//            while (!Thread.currentThread().isInterrupted()) {
//                System.out.println(++i);
//            }
//            System.out.println("响应线程终止请求.");
//        });
//        thread.start();
//
//        TimeUnit.SECONDS.sleep(1L);
//
//        thread.interrupt();
//
//        Thread.currentThread().isInterrupted();
//        Thread.interrupted();
//        thread.interrupted();
//    }


    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.futao.learn.lagou.StopThread");
    }

    @Override
    public void a() throws RuntimeException {

    }
}
