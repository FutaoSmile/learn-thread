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
    
    
* 相关方法的使用
    * thread.isInterrupted()
    * thread.interrupted()
    * Thread.interrupted()





--------

### # 面试题：
* 如何正确地停止/中断一个线程
* 哪些情况下线程会停止
* 如何处理不可中断的阻塞

### # 核心思想
* **使用`interrupt()`来通知，而不是强制。**

### # 代码演示
* 场景1：run()方法中没有`sleep()/wait()`等会响应中断的方法。
  1.1  线程未处理中断：
```java
/**
 * 正确停止线程---run()方法内没有sleep()或者wait()方法-未处理中断信号
 *
 * @author futao
 * @date 2020/6/6
 */
public class StopThreadWithoutSleepWait implements Runnable {

    @Override
    public void run() {
        unHandleInterrupt();
    }

    /**
     * 未处理中断
     */
    public void unHandleInterrupt() {
        int num = 0;
        //打印最大整数一半的范围内10000的倍数
        while (num <= Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000倍数");
            }
            ++num;
        }
        System.out.println("任务执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithoutSleepWait());
        //启动线程
        thread.start();
        //增加子线程处于运行状态的可能性
        Thread.sleep(500L);
        //尝试中断子线程
        thread.interrupt();
    }
}
```
* *期望：子线程在执行500毫秒之后停下来。*
* 结果：线程并没有停下来。原因是：我们并未处理线程的中断信号。
![image.png](https://upload-images.jianshu.io/upload_images/1846623-e07b69b2db0dc82b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  1.2 对程序进行改进：响应中断。
    * 在while循环条件中判断当前线程是否被中断(*`Thread.currentThread().isInterrupted() `*)，如果未被中断才继续执行，被中断则跳出while循环。
```java
package com.futao.learn.threads.c_如何停止线程;

/**
 * 正确停止线程---run()方法内没有sleep()或者wait()方法
 *
 * @author futao
 * @date 2020/6/6
 */
public class StopThreadWithoutSleepWait implements Runnable {

    @Override
    public void run() {
        handleInterrupt();
    }

    /**
     * 响应中断
     */
    public void handleInterrupt() {
        int num = 0;
        //加入线程未被中断的条件
        while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000倍数");
            }
            ++num;
        }
        System.out.println("任务执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadWithoutSleepWait());
        //启动线程
        thread.start();
        //增加子线程处于运行状态的可能性
        Thread.sleep(500L);
        //尝试中断子线程
        thread.interrupt();
    }
}
```
* *期望：线程在500毫秒之后响应中断，停下来。*
* 结果：线程成功响应中断，提前结束。
![image.png](https://upload-images.jianshu.io/upload_images/1846623-5498984b0dd12e9d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* **总结可得出：线程调用者可以向线程发出中断请求，但是线程中断的权利控制在线程代码的编写者是否响应了你的中断请求。线程代码的编写者比调用者更加了解线程应不应该被停止，何时停止。**

* 场景2：run()方法中存在`sleep()/wait()`等会响应中断的方法。(*响应中断的方法会抛出`InterruptedException`*)
  2.1 `sleep()`在while循环外
```java
package com.futao.learn.threads.c_如何停止线程;

/**
 * 中断线程-run()方法中有sleep()或者wait()方法
 *
 * @author futao
 * @date 2020/6/6
 */
public class StopThreadWithSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num <= 300) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的整数倍");
                }
                ++num;
            }
            try {
                //sleep()方法会响应中断，且响应中断的方式为抛出InterruptException异常--- sleep interrupted
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行完毕");
        });
        //启动线程
        thread.start();
        //等待while循环执行完毕
        Thread.sleep(200L);
        //当线程处于sleep()状态时进行中断
        thread.interrupt();
    }
}
```
* *预期：程序执行完while循环之后，阻塞在`sleep()`方法，此时进行中断，`sleep()`方法响应该中断，抛出`InterruptedException `，打印异常堆栈。*
* *测试：符合预期。*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-81c62e714fd1edca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  
  2.2 无法停止的线程：sleep()方法在while循环内。
  * 你预期下面代码的执行结果是怎样的？
```java
/**
 * 3. 无法停止的线程
 *
 * @author futao
 * @date 2020/6/6
 */
public class CantStopThread {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 1;
            while (num <= 1000 && !Thread.currentThread().isInterrupted()) {
                if (num % 2 == 0) {
                    System.out.println(num + "是2的整数倍");
                }
                ++num;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程执行完毕");
        });

        //启动线程
        thread.start();
        //主线程休眠500毫秒
        Thread.sleep(500L);
        //中断线程
        thread.interrupt();
    }
}
```
* *预期：线程在第一次进入while循环时，进入休眠1000毫秒状态，在500毫秒时主线程向子线程发出中断信号，sleep()方法响应中断，打印异常堆栈，下次再进入while循环时，因为线程被设置成了中断状态，所以while中条件不成立，不应该继续执行。*但是实际上是这样吗？
* *结果：slee()响应了中断，打印了异常堆栈。但是线程并没有停下来，而是继续执行。就像什么都没有发生一样。*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-96688dbd8513419e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* **原因：sleep()在响应了中断之后，清除了线程的中断状态。那么while判断时不知道线程被中断了。**
* *查看sleep()方法的描述：当`InterruptedException`异常被抛出后，线程的中断状态将被清除。*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-96a67a74e930c6ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* *类似的，查看`Object.wait()`的方法描述。*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-46177bd261627dc2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 类似的会响应中断的方法还有那些？
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
* 那么，线程响应中断后应该怎么处理。
### # 线程中断的最佳实践：
  * 传递中断
  * 不想或无法传递：恢复中断
  * 核心思想：**不应屏蔽中断**

1. 传递中断：在方法签名中将中断异常抛出，而不是生吞，交给调用者处理。

```java
/**
 * 正确停止线程的方式1-抛出中断
 * 优先在方法签名中抛出该异常
 *
 * @author futao
 * @date 2020/6/6
 */
public class RightWayToStopThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("running...");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("响应中断，跳出循环，停止线程");
                break;
            }
        }
    }

    /**
     * 业务方法应该将中断异常抛出，将异常传递给上层--传递中断
     *
     * @throws InterruptedException
     */
    private void throwInMethod() throws InterruptedException {
        System.out.println("业务执行中.....");
        Thread.sleep(2000L);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayToStopThread());
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }
}
```
* *结果:*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-78136ec2ccd31ecb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 不想或无法传递时：应该恢复中断(*`Thread.currentThread().interrupt()`*)
```java
/**
 * 正确停止线程的方式2
 * 恢复中断
 *
 * @author futao
 * @date 2020/6/6
 */
public class RightWayToStopThreadReInterrupt implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("running...");
            throwInMethod();
        }
        System.out.println("线程任务执行完毕");
    }

    private void throwInMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.out.println("感知到中断请求。");
            System.out.println("重新设置中断信号");
            //尝试恢复中断
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayToStopThreadReInterrupt());
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }
}
```
* *结果:*
![image.png](https://upload-images.jianshu.io/upload_images/1846623-23ca938e2edeea7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### # 线程中断的相关方法
* 预期下面代码的执行结果？
```java
/**
 * 线程中断的相关方法
 *
 * @author futao
 * @date 2020/6/7
 */
public class InterruptMethod {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("线程任务执行中...");
            while (true) {
            }
        });

        //启动线程
        thread.start();
        System.out.println(thread.isInterrupted());
        //向线程发送中断信号
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println(thread.isInterrupted());
        System.out.println(Thread.interrupted());
        System.out.println(thread.isInterrupted());
        System.out.println(thread.interrupted());
        System.out.println(thread.isInterrupted());
    }
}
```
* *结果：*
```log
false
true
true
false
true
false
true
```
* *分析：*
  1. 线程处于运行状态，且没有程序给线程发送中断信号。所以`非中断状态`。
  2. 调用了中断方法，所以`线程状态状态为true`。
  3. 由于`thread.isInterrupted()`并不会清除线程的中断状态，所以多次调用，返回的结果一样，依旧为`已中断`。
  4. `Thread.interrupted()`判断的是执行这行代码的线程的中断状态。这里是主线程，所以为未中断。**且该方法调用之后，会将执行该方法的线程的中断状态清除。**
  5. 因为`Thread.interrupted()`清除的是执行代码的线程的中断状态，所以不印象子线程的中断状态，所以子线程的中断状态仍然为true。
  6. 如果子线程对象直接调用静态方法`interrupted()`，返回的也是执行这段代码的线程的中断状态。此时为主线程，状态为未中断。
  7. 子线程对象直接调用静态方法`interrupted()`并不会清除调用对象的线程中断状态，而是清除执行这段代码的线程的中断状态。所以子线程的中断状态不影响。
* 为什么通过子线程对象来执行静态方法`static boolean interrupted()`清除的是执行者的中断状态呢?查看源码发现，静态方法`static boolean interrupted()`会先获取到当前执行这段代码的线程，清除其中断状态，并返回中断状态。
![image.png](https://upload-images.jianshu.io/upload_images/1846623-b92fbd45647509a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* **总结:**
  1. `thread.interrupt()`  给线程发送中断信号，设置线程`thread`的中断状态为true。
  2.  `thread.isInterrupted()`  判断线程`thread`是否被中断。且不改变线程的中断状态
  3. `Thread.interrupted()/thread.interrupted()` 判断执行这行代码的线程的中断状态，并且清除其中断状态。
  4. `private native boolean isInterrupted(boolean ClearInterrupted);` native方法，真正判断线程中断状态和清除中断状态的代码。*`thread.isInterrupted()`和`Thread.interrupted()/thread.interrupted()`最终调用的都是这个方法。*

*  Q：如何清除线程的中断状态? 执行`Thread.interrupted();`这行代码的线程的中断状态会被清除。


### # 哪些情况下线程会停止
  1. 线程`run()`方法正常执行完毕。(*可借助线程中断机制提前结束run()方法*)
   2. 线程发生了未捕获的异常。

### # 错误的停止线程的方式
  * 被弃用的`stop()`，`suspend()`和`resume()`
  * 使用volatile设置boolean标记位的方式，不可靠

### # 如何处理不可中断的阻塞
* 并不是所有的阻塞都会响应中断，例如IO中的`InputStream.read()`。处理这类问题的方式要视情况而定，大概思路是手动编写程序检测线程的中断状态，如果线程被中断，则手动调用例如`InputStream.close()`方法来关闭流，实现停止线程。

### # 本文源代码
[https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/c_%E5%A6%82%E4%BD%95%E5%81%9C%E6%AD%A2%E7%BA%BF%E7%A8%8B](https://github.com/FutaoSmile/learn-thread/tree/master/src/main/java/com/futao/learn/threads/c_%E5%A6%82%E4%BD%95%E5%81%9C%E6%AD%A2%E7%BA%BF%E7%A8%8B)

### # 系列文章
[Java多线程：线程的创建与启动](https://www.jianshu.com/p/5c0f29e3e30e)


![image.png](https://upload-images.jianshu.io/upload_images/1846623-73d38e033a6b7b95.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/1846623-bea8ee98bd722cd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1846623-f0904af9c442f3a7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
