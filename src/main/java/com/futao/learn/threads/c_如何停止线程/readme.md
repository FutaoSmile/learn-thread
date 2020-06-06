#### # 常见面试问题
* 如何停止一个线程
* 如何处理不可中断的阻塞
* 哪些情况下线程会停止


* 如何停止一个线程：使用interrupt()来通知，而不是强制。

* 开过过程中，中断线程的最佳实现
    1. 传递中断
    2. 不想或无法传递：恢复中断
    3. 不应屏蔽中断

* 是否中断线程在权利在于线程本身。

* 响应中断的方法总结
    * Object.wait()/wait(long)/wait(long,int)
    * Thread.sleep(long)/sleep(long,int)
    * Thread.join()/join(long)/join(long,int)
    * juc.BlockingQueue.take()/put(E)
    * juc.Lock.lockInterruptibly()
    * juc.CountDownLatch.await()
    * juc.CyclicBarrier.await()
    * juc.Exchanger.exchange(V)
    * jio.InterruptibleChannel相关方法
    * jio.Selector相关方法

* 哪些情况下线程会停止
    1. 线程run()方法正常执行完毕。
    2. 线程发生了未捕获的异常。
    
* 错误的停止线程的方式
    * 被弃用的stop(),suspend()和resume()
    * 使用volatile设置boolean标记位的方式，不可靠