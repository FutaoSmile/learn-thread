package com.futao.learn.threads.a_创建线程;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author futao
 * @date 2020/6/6.
 */
public class CallableFutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("666");
                Thread.sleep(3000L);
                return Thread.currentThread().getName();
            }
        });
        new Thread(task).start();
        //将会阻塞，直到线程执行完毕
        System.out.println(task.get());
    }
}
