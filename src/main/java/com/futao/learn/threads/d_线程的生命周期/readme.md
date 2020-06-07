### # 线程的生命周期
* 线程有哪几种状态
* 每个状态的含义
* 状态之间的转化过程，图示


### # Detail
* 线程有哪几种状态
    1. New (*刚创建出线程实例*)
        * `new Thread()`。
    2. Runnable (*可运行状态，等待CPU的调度*)(要注意：即使是正在运行的线程，状态也是Runnable，而不是Running)
        * 调用了`thread.start()`启动线程。
        * 被`synchronized`标记的代码获取到同步监视器
        * `obj.notify()`唤醒线程。
        * `obj.notifyAll()`唤醒线程。
        * `obj.wait(time), thread.join()`等待时间time耗尽。
    3. Blocked  (*阻塞状态*)
        * 被synchronized标记且未获取到同步监视器。
    4. Waiting  (*不超时等待状态*)
        * threadA中调用`threadB.join()`，threadA将Waiting，直到threadB终止。
        * `obj.wait()`  释放同步监视器obj，并进入阻塞状态。
    5. TimedWaiting (*等待指定时间time*)。
        * threadA中调用`threadB.join(time)`。
        * `obj.wait(time)`
        * `sleep(time)`。
    6. Terminated   (*线程终止*)
        * 线程正常执行完毕。
        * 发生了未捕获的异常。


