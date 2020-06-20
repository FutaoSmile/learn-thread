package com.futao.learn.threads.d_线程的生命周期;

/**
 * 线程的状态演示：NEW，Runnable，Terminated
 *
 * @author futao
 * @date 2020/6/7
 */
public class NewRunnableTerminated implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new NewRunnableTerminated());

        System.out.println("因为还未调用start()，所以预期`New`：" + thread.getState());
        thread.start();
        //等待子线程跑一会，看看运行中的状态是不是Runnable
        Thread.sleep(10L);
        System.out.println("线程start()之后，预期Runnable：" + thread.getState());

        //等待子线程执行完毕，再查看其状态
        Thread.sleep(1000L);
        System.out.println("线程任务执行完毕，预期Terminated：" + thread.getState());
    }
}
