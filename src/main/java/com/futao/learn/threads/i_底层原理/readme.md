* 重排序
    * 执行顺序与代码顺序不一致
    
* 可见性
    * volatile 
        * 数据在核心本地缓存修改之后会强制刷新到主存，共享内存
    * happens-before
        * happens-before是用来解决可见性问题的：在时间上，动作A发生在动作B之前，B保证能看见A。
        * 如果一个 操作happens-before于另一个操作，那么可以说第一个操作对于第二个操作是可见的。
        * 场景
            * 单线程
            * 锁操作 synchronized和lock可以保证
                * 一个线程获取lock之后，一定可以看见上一个线程释放锁之后的所有变化
            * volatile
            * 工具类
                * 线程安全的容器
                * CountDownLatch
                * Semaphore 信号量
                * CyclicBarrier
                * Future
                * 线程池
            
* volatile
    * 作用
        * 可见性
        * 禁止重排序
    * 不适用于i++
        * 无法保证原子性
    * 适用场景
        * boolean flag 标记位
        * 触发器
            * 刷新volatile之前的变量的值 