package com.futao.learn.threads.i_底层原理;

/**
 * 双重检查锁实现的单例
 * 特点：
 * 线程安全
 * 延迟加载
 * 效率较高
 *
 * @author futao
 * @date 2020/7/29
 */
public class SingletonDoubleCheck {

    /**
     * 使用volatile的原因：
     * instance = new SingletonDoubleCheck()并不是一个原子操作，在JVM中发生了三件事情
     * 1, 给instance分配内存
     * 2. 调用SingletonDoubleCheck的构造方法并初始化成员变量
     * 3. 将instance对象指向分配的内存空间（执行完之后instance就是非null了）
     * 但是实际运行过程中，23的顺序可能颠倒，导致线程1执行完3之后，并没有初始化2。导致线程2判断instance不是null，获取到了这个没有完全初始化的单例对象
     * <p>
     * 即：阻止JVM对对象的初始化过程重排序，保证对象完全初始化
     */
    private volatile static SingletonDoubleCheck instance;

    private SingletonDoubleCheck() {
    }

    public static SingletonDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleCheck.class) {
                if (instance == null) {
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                System.out.println(SingletonDoubleCheck.getInstance());
            }).start();
        }
    }
}
