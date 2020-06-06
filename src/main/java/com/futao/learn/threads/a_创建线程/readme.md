* 实现方式的比较，哪种更好
    * 两类，Oracle官方文档这么写的
* 两种方法的本质对比
    * extends Thread: 整个run()方法被重写；
    * implements Runnable: 最终调用target.run(); target为构造方法中传入的Runnable对象；
    
* **准确地讲，创建线程的唯一方式就是创建一个Thread对象，调用start()启动线程（`new Thread().start()`）。而实现线程的执行单元(即线程任务)的方式有两种：**
    1. **[实现Runnable接口](ByRunnable.java)，重写Runnable.run()方法，将线程任务写到run()方法中。在线程Thread对象创建之后，调用(Runnable)target.run()执行任务。**
    2. **[继承Thread类](ByThread.java)，重写Thread.run()方法，将线程任务写到run()方法中。**
    
* [如果同时实现Runnable接口和继承Thread](Both.java)，会发生什么
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
    
    
    
------

### # 面试题：
* Java中创建线程有几种方式。
* 不同的创建方式有什么区别。
* 如何启动一个线程。

### # Java中创建线程的方式
* JDK源码中的描述：**两种**

![image.png](https://upload-images.jianshu.io/upload_images/1846623-8e0fed1b77206301.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  * **第一种是继承Thread类，重写其run()方法()。**
  * **第二种是实现Runnable接口，重写run()方法，再将Runnable实例传给Thread，Thread类最终会调用`target.run()`(*target即为Runnable实例*)方法来执行。**
![image.png](https://upload-images.jianshu.io/upload_images/1846623-b3659db8d4d283ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 代码演示
```java
/**
 * 通过继承Thread类，重写run()方法实现的线程
 *
 * @author futao
 * @date 2020/6/4
 */
public class ByThread extends Thread {

    @Override
    public void run() {
        System.out.println("通过继承Thread实现的多线程:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new ByThread().start();
    }
}


/**
 * 通过实现Runnable接口，重写run()方法实现的线程
 *
 * @author futao
 * @date 2020/6/4
 */
public class ByRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("通过实现接口Runnable实现的多线程:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new Thread(new ByRunnable()).start();
    }
}
```

### # 核心思想
**从源代码的角度来看，创建线程的方式只有一种，唯一的途径就是实例化一个Thread对象，通过`thread.start()`来启动线程。而继承Thread类和实现Runnable接口，只不过是对方法执行单元(*即`run()`方法*)的两种不同实现。**

### # 两种方式的对比：
1. **从代码架构角度：**使用Runnable接口得我方式可以将线程的创建/停止/状态管理等与真正的业务逻辑解耦，使Runnable子类只需要关注真正的业务即可。
2. **从性能损耗角度：**使用继承Thread的方法，每次执行任务都需要启动一个新的线程，创建一个新的Thread实例，任务执行完毕之后还需要进行销毁。对性能的损耗比较严重。而实现Runnable接口的方式，可以实现对线程的复用，每次给线程传递不同的任务(Runnable实例)即可，不需要频繁的创建与销毁线程。（[参考线程池的执行过程](https://mp.weixin.qq.com/s/jISHo8-aKMPjjeCYGJILgg)）
3. **从可扩展性角度：**Java只支持单继承，所以实现Runnable接口的方式更好，避免继承的局限，方便后续对程序进行扩展。

### # Q: 如果同时继承Thread类和实现Runnable接口，会发生什么？
```java
/**
 * @author futao
 * @date 2020/6/4
 */
public class Both {

    public static void main(String[] args) {
        new Thread(() -> {
            //通过lambda表达式创建Runnable子类对象
            System.out.println("来自实现Runnable接口的run()方法");
        }) {
            //Thread的匿名内部类，直接重写Thread父类的run()方法
            @Override
            public void run() {
                System.out.println("来自重写Thread类的run()方法");
            }
        }.start();
    }
}
```

![image.png](https://upload-images.jianshu.io/upload_images/1846623-10d9d7008e9a9492.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* **从结果可以看到，最终线程执行的是匿名内部类的run()方法。原因是在我们将Thread的start()方法重写之后不会再执行调用Runnable.run()方法。而执行我们重写之后的run()方法。**
```java
  @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
```

### # 错误观点
* 线程池: 通过线程工厂`ThreadFactory.newThread()`创建线程，而`ThreadFactory.newThread()`方法中也是通过实例化Thread对象的方式创建线程。
```java
       ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
```
* 通过`FutureTask<>`与`Callable`: FutureTask实际上实现了Runnable接口，并且在重写的run()方法中调用了传递进来的Callable对象的call()方法。所以这种方式只不过是对Runnable方式的一种封装而已。本质上也只是实现线程执行单元的一种方法，最终需要将FutureTask对象传入Thread()对象进行执行。
```java
/**
 * @author futao
 * @date 2020/6/6.
 */
public class CallableFutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("666");
                Thread.sleep(3000L);
                return Thread.currentThread().getName();
            }
        });
        new Thread(task).start();
        //将会阻塞，直到线程任务执行完毕
        System.out.println(task.get());
    }
}
```
![image.png](https://upload-images.jianshu.io/upload_images/1846623-2ace24b5fbeccb6b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1846623-e201cc57a86cf156.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### # 线程的启动
* 调用Thread对象的`start()`方法。三个过程:
  1. 判断线程状态。 
  2. 加入线程组。
  3. 执行native方法`start0()`。
* 直接调用`run()`方法：只是普通方法调用，不会开启新的线程。
* `start()`方法只能被调用一次，如果第二次调用，将抛出异常，即启动过程的第一步：检查线程状态不通过。

### # 本文源代码
  * [https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/a_%E5%88%9B%E5%BB%BA%E7%BA%BF%E7%A8%8B](https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/a_%E5%88%9B%E5%BB%BA%E7%BA%BF%E7%A8%8B)

  * [https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/b_%E5%90%AF%E5%8A%A8%E7%BA%BF%E7%A8%8B](https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/b_%E5%90%AF%E5%8A%A8%E7%BA%BF%E7%A8%8B)



![image.png](https://upload-images.jianshu.io/upload_images/1846623-73d38e033a6b7b95.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/1846623-bea8ee98bd722cd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)