package com.futao.learn.lagou;

import java.util.concurrent.*;

/**
 * 创建线程的方式只有一种，就是构造一个Thread对象并调用start()方法，但是创建线程执行单元的方式却有多种，比如
 * 1. 继承Thread重写run()方法
 * 2. 实现Runnable接口，实现run()方法
 * 3. 实现Callable接口实现call()方法，并创建FutureTask对象，将FutureTask对象交给Thread进行处理，其实FutureTask就是个Runnable，实现了Runnable接口
 * <p>
 * 实现接口的方式的优点是：
 * 1. Java单继承，实现接口易于扩展
 * 2. Runnable接口中只有一个run()方法，这样这个类中只需要编写业务逻辑即可，线程的状态等信息在Thread中维护，符合单一职责原则
 * 3. 便于对象的复用，比如一个runnable对象可以交给线程池多次使用。
 *
 * @author futao
 * @date 2020/10/16
 */
public class Creator implements Runnable {

    @Override
    public void run() {
        System.out.println("gogogo");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        new Thread(new Creator()).start();

        new ChildThread().start();

        Call call = new Call();
        FutureTask<String> stringFutureTask = new FutureTask<>(call);

        new Thread(stringFutureTask).start();

        String s = stringFutureTask.get();
        System.out.println(s);


        ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread() {
                    @Override
                    public void run() {
                        System.out.println("gogogo");
                    }
                };
            }
        });
        executorService.submit(stringFutureTask);
    }
}


class ChildThread extends Thread {

    @Override
    public void run() {
        System.out.println("gogogo");
    }
}

class Call implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "GOGOGO";
    }
}