### # 如何处理线程未捕获的异常
* 主线程可以轻松的获取异常，子线程却不行。
* 无法在一个线程中获取另外一个线程的异常。

### # 解决方案
* 用try catch将run()方法整个方法体包住。
    * 缺点：每个线程的run()方法都需要编写重复的处理代码。
* 使用UnCaughtException
```java
    @FunctionalInterface
    public interface UncaughtExceptionHandler {
        /**
         * Method invoked when the given thread terminates due to the
         * given uncaught exception.
         * <p>Any exception thrown by this method will be ignored by the
         * Java Virtual Machine.
         * @param t the thread
         * @param e the exception
         */
        void uncaughtException(Thread t, Throwable e);
    }
```

* 自定义线程异常处理器->实现`UnCaughtException`
    * 给所有线程设置
    * 给每个线程单独设置
    * 给线程池设置