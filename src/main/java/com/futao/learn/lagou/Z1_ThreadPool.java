package com.futao.learn.lagou;

import java.util.List;
import java.util.concurrent.*;

/**
 * 不使用线程池的危害:
 * 1. 需要创建大量的线程，消耗系统资源，并且每个程序和系统的线程资源都是有限的，不能无限创建线程。
 * 2. 创建了大量的线程会导致线程上下文切换频繁，导致系统不稳定，变慢。
 * 3. 无法重复使用线程。
 * <p>
 * *** RejectHandler拒绝策略
 * 1. 抛出异常
 * 2. 直接丢弃当前提交的任务
 * 3. 丢弃队列头，即存活时间最长的任务
 * 4. 将任务交给提交这个任务的线程去处理
 * * * 这种方式比较好，一方面可以保证任务不丢失，且可以降低任务的提交速度
 * <p>
 * *** 常见的线程池？
 * 1. FixedThreadPool 固定线程数的线程池corePoolSize=maxPoolSize
 * 2. CachedThreadPool 缓存线程池 maxPoolSize=Integer.MAX_VALUE 所以可以创建几乎无限个线程，并且使用的队列是传递队列，无存储任务，只是传递任务给线程池
 * 3. ScheduledThreadPool 能执行定时任务的线程池
 * 4. SingleThreadExecutor 单线程线程池 只有一个线程，可以保证任务有序执行
 * 5. SingleThreadScheduleExecutor
 * 6. ForkJoinPool 可以将任务拆分成小任务，利用多核CPU的优势，拆分计算再汇总，并且内部可以保证，每个线程执行的负载差不多（每个线程都有自己的工作队列），比如某个线程执行的比较快，先把任务执行完了，会去从其他线程的工作队列偷一个任务来执行。
 * <p>
 * *** 线程池中常用的阻塞队列
 * 1. LinkedBlockingQueue 无界的阻塞队列   FixedThreadPool
 * 2. SynchronousQueue  传递队列，不存储任务  CachedThreadPool
 * 3. DelayedWorkQueue  延迟工作队列，支持延迟执行任务
 * <p>
 * *** 为什么不要使用自动创建线程池，而要手动创建线程池
 * 以为不可控，
 * * FixedThreadPool，SingleThreadExecutor，ScheduledThreadPool都使用了无界队列LinkedBlockingQueue，容易导致OOM
 * * CachedThreadPool 因为可能创建无限多个线程，也可能导致OOM，系统资源耗尽，系统崩溃
 * <p>
 * *** 线程数如何设置
 * 线程数 = CPU 核心数 *（1+平均等待时间/平均工作时间）
 * 如果是CPU密集型，设置为CPU核心数的1-2倍
 * 如果是IO密集型，设置为CPU核心数的很多倍
 * <p>
 * *** 停止线程
 * * shutdown() 开始停止线程，线程池将继续执行任务，将工作队列中的任务执行完，但是不接收新的任务，提交新的任务将执行拒绝策略
 * * isShutdown()
 * * isTerminated()
 * * shutdownNow()  立刻停止线程，会向当前正在执行的线程发送中断信号，任务队列中的线程将被返回，可再次补救
 * * awaitTermination() 阻塞当前线程，等待timeout时间，判断线程池是否terminated，有可能被中断，或者提前停止
 *
 * @author futao
 * @date 2020/10/21
 */
public class Z1_ThreadPool {

    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r);
//            }
//        }, new ThreadPoolExecutor.CallerRunsPolicy());
//
//        threadPoolExecutor.execute(() -> {
//            System.out.println("---");
//        });
//
//        threadPoolExecutor.shutdown();
        Z1_ThreadPool.shutDownThreadPool();

    }


    public static void shutDownThreadPool() throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        //线程池是否停止，或者处于停止的过程当中
        System.out.println(fixedThreadPool.isShutdown());
        // 判断线程是否真的停止了
        System.out.println(fixedThreadPool.isTerminated());

        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 开始停止线程，线程池将继续执行任务，将工作队列中的任务执行完，但是不接收新的任务，提交新的任务将执行拒绝策略
        fixedThreadPool.shutdown();
        // 正在执行停止动作，所以是true
        System.out.println(fixedThreadPool.isShutdown());
        // 但是线程还并未真正完全停下来
        System.out.println(fixedThreadPool.isTerminated());

        //立刻停止线程，会向当前正在执行的线程发送中断信号，任务队列中的线程将被返回
        List<Runnable> runnables = fixedThreadPool.shutdownNow();
        System.out.println(fixedThreadPool.isShutdown());
        System.out.println(fixedThreadPool.isTerminated());

        TimeUnit.SECONDS.sleep(2L);

        // 阻塞当前线程，等待timeout时间，判断线程池是否terminated，有可能被中断，或者提前停止
        boolean b = fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println(fixedThreadPool.isShutdown());
        System.out.println(fixedThreadPool.isTerminated());


    }
}
