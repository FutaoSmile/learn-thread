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
    
    