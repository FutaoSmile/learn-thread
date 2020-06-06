* 实现方式的比较，哪种更好
    * 两类，Oracle官方文档这么写的
* 两种方法的本质对比
    * extends Thread: 整个run()方法被重写；
    * implements Runnable: 最终调用target.run(); target为构造方法中传入的Runnable对象；
    
* **准确地讲，创建线程的唯一方式就是创建一个Thread对象，调用start()启动线程（`new Thread().start()`）。而实现线程的执行单元(即线程任务)的方式有两种：**
    1. **[实现Runnable接口](ByRunnable.java)，重写Runnable.run()方法，将线程任务写到run()方法中。在线程Thread对象创建之后，调用(Runnable)target.run()执行任务。**
    2. **[继承Thread类](ByThread.java)，重写Thread.run()方法，将线程任务写到run()方法中。**
* 如果同时实现Runnable接口和继承Thread，会发生什么
    * 会走重写Thread类的run()方法逻辑，因为Thread类中的run()方法已经被重写，所以不会再执行`target.run()`
    
    
* 错误观点
    * [线程池](ThreadPool.java): 本质是通过线程工厂中`new Thread()`创建的线程。
    * [FutureTask<>与Callable](./CallableFutureTask.java)，FutureTask间接实现了Runnable接口，本质上也只是实现线程执行单元的一种方法，最终需要将FutureTask对象传入Thread()对象进行执行。
    
* 本质只有一种方法，那就是创建Thread()对象。但是通常区分为两种形式。另外还有其他表现形式，例如线程池，FutureTask等
* 继承Thread和Runnable方式的对比：
    1. 从代码架构角度：解耦。通过实现Runnable接口的方式可以让程序只关注具体的线程任务，而不需要关心线程的启动执行状态和销毁。
    2. 从性能损耗角度: （没搞明白实现Runnable接口的方式是如何实现线程的复用的？？？？？）
        * 继承Thread的方式，每次创建新的线程都需要实例化Thread子类对象。
        * 实现接口的方式，可以复用Runnable子类对象。
    3. Java只支持单继承，所以实现Runnable接口的方式更好，避免继承的局限。