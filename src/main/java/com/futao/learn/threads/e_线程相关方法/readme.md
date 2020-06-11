### # Object中的方法
* wait()/wait(time)
* notify()/notifyAll()

### # 面试问题
* 交替打印数字
* 手写生产者消费者设计模式
* 为什么`wait()`需要在同步代码块内使用，而`sleep()`不需要。
* 为什么线程通信的方法`wait()`,`notify()`,`notifyAll()`定义在Object类里，而`sleep()`定义在Thread类中。
* 如果调用`thread.wait()`会怎样。


### # Sleep()方法
* 作用：使得线程进入`TimedWaiting`状态，并且不占用CPU资源。
* 不释放锁，`synchronized`和`Lock`
* 和`wait()`的区别 
* sleep()方法会响应中断
    * 抛出InterruptException()。
    * 清除线程的中断状态。
    
* **一句话总结**
    * `sleep(time)`方法可以使当前线程休眠，进入`TimedWaiting`状态，但是不释放所持有的`synchronized`和`Lock`锁。
    * time时间内，当前线程不占用CPU资源，且如果此时线程被中断，`sleep()`方法将抛出`InterruptException`异常并清除中断状态。
    * time时间之后， 线程将结束`TimedWaiting`状态。
    
* **sleep()与wait()的比较?**
    * 相同点：
        * 都会让当前线程进入`Waiting`/`TimedWaiting`状态。
        * 都会响应中断，抛出InterruptException`，清除线程中断状态。
    * 不同点：
        * `sleep()`属于`Thread`类，`wait()`属于`Object`类。
        * `wait()`需要在synchronized`代码快内使用。
        * `wait()`方法会释放同步监视器，而`sleep()`不会。
        
    
### # join()方法
* 普通用法
* join()遇到中断：中断的是哪个线程？
* 在join()期间，线程是什么状态
> 一句话： 让一个线程等待另一个线程执行完毕。
* 源码分析，内部使用的是wait()，但是没有notify()===> join()结束之后会调用notifyAll()
* 原理之后，手动实现join()，通过wait()与notify()
```java
x.join=>
synchronized(thread){
    thread.wait();
}
```


### # yield()
* 释放我的CPU时间片
* JVM不保证