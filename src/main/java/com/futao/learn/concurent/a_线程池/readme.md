* 线程池的好处
    * 加快响应速度
    * 合理利用CPU和内存
    * 统一管理
    
* 创建和停止线程池
    * 核心参数
        * corePoolSize
        * maxPoolSize
        * workQueue(size)
            * 直接交换  SynchronousQueue 内部大小为0，无法存储任务，中转队列
            * 无界队列(maxPoolSize无效，没有容量上限，当心OOM)  没有设置容量（默认为Integer.MAX_VALUE，可以理解为无限大）的LinkedBlockingQueue
            * 有界队列  ArrayBlockingQueue, 设置了容量的LinkedBlockingQueue
        * keepAliveTime
        * TimeUnit
        * ThreadFactory
        * RejectHandler
    * 流程
        * 向线程池提交任务，判断corePoolSize是否满了，如果没满则创建新的线程来执行任务，如果满了，则判断workQueue(size)是否满了，如果没满，则将任务添加到队列中，如果满了，则判断maxPoolSize是否满了，如果没满，则创建新的线程来执行任务，如果满了，则执行设定的拒绝策略。如果线程池的线程个数多于corePoolSize，那么多于的线程空闲时间超过设定的keepAliveTime将被回收（可以画个流程图）
    * Executors创建线程及特点
        * FixedThreadPool   固定线程数量的线程池
            * corePoolSize=maxPoolSize=n
            * keepAliveTime=0 线程一直存活，知道手动终止线程池
            * workQueue：LinkedBlockingQueue，任务过多有发生OOM的可能
        * SingleThreadExecutor   只有一个线程的线程池
            * corePoolSize=maxPoolSize=1
            * keepAliveTime=0 线程一直存活，知道手动终止线程池
            * workQueue：LinkedBlockingQueue，任务过多有发生OOM的可能
        * CachedThreadPool   缓存线程池
            * 线程数量最大为最大整型个，具有自动回收多余线程的特点
            * corePoolSize=0
            * maxPoolSize=Integer.MAX_VALUE 整型最大值
                * 创建的线程过多可能导致OOM
            * keepAliveTime 60s
            * workQueue：SynchronousQueue
                * 向线程池提交任务后直接交给线程去处理
        * ScheduledThreadPool 
            * 支持定时和周期性的执行任务
            * workQueue：DelayWorkQueue
    * 线程数量的设定
        * CPU 密集型，如加密，计算hash等：CPU核心数的1-2倍
        * 耗时IO型，读写数据，文件，网络读写。CPU核心数*(1+平均等待时间/平均工作时间)
    * 停止线程池
        * `shutdown()`
            * 调用之后，线程池中的存量任务会继续执行完毕，不再接受新的任务，提交新的任务会走RejectHandler
        * `shutdownNow()`
            * （希望）立刻停止线程。
            * 原理：
                1. 向线程发送interrupt中断信号。
                2. 工作队列中的线程将在方法返回值中返回List<Runnable>。
        * `isShutdown()`
            * 判断线程池是否进入了停止状态（线程可能并未真正结束）。
        * `isTerminated()`
            * 判断线程池是否完全终止。
        * `awaitTermination(timeout,TimeUnit)`
            * 阻塞判断判断线程池在timeout时间**之内**是否终止。
                * 如果线程在timeout时间之内停止了，则立刻返回true。
                * 如果线程在timeout时间内未停止，则在等待timeout时间之后返回false。
    * 拒绝策略`RejectedExecutionHandler`
        * 
    * 钩子方法
 ```java
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info("线程执行任务之前");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        log.info("线程执行任务之后");
    }

    @Override
    protected void terminated() {
        super.terminated();
        log.info("线程池终止");
    }
```
            
        