### # 线程属性
* id: 
    * 线程唯一标识。不允许修改。
* name: 
    * 线程的名字，可以自定义成有具体含义的名字，便于识别不同作用的线程。（可同名）
* isDaemon: 
    * 是否是守护线程。
    * true=守护线程，false=用户线程。
    * 当JVM中所有的线程都是守护线程，JVM将退出。
    * 具有代表性的线程: main线程:用户线程，gc线程:守护线程。
    * 子线程会默认继承父线程的这个属性。
    * 必须在调用`start()`之前设置这个属性，线程运行中设置线程守护属性会抛出异常。
* priority
    * 线程优先级。
    * 优先级高的线程概率上会优先运行。并不可靠.
    * Java中的线程优先级有10个，默认是5，且子线程会继承父线程的优先级。
    * 不可靠：java中的线程优先级有10个，但是OS的线程优先级并不一定是10个，所以存在java中好几个优先级对应OS中的同一个优先级，所以不可靠。
        * 所以程序不应该依赖优先级。
        * 优先级一般默认设置成5即可。
    
    
### # ID
* 源码
```java
        /* Set thread ID */
        tid = nextThreadID();


    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

```
* *线程初始化方法`init()`会给线程设置id，该id通过被synchronized标记的`nextThreadID()`方法获取，id自增。*

### # NAME
* 源码:
![image.png](https://upload-images.jianshu.io/upload_images/1846623-b0c792ce51ce23ae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 如果没有指定线程的名称，则默认是`"Thread-" + nextThreadNum()`。`nextThreadNum()`为线程安全的一个自增。
```java
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }
```
* 还可以通过`thread.setName()`设置线程名称。