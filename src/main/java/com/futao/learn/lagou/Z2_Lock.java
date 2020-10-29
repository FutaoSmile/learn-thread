package com.futao.learn.lagou;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author futao
 */
public class Z2_Lock {

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        Lock lock = new ReentrantLock();
//        lock.lock();
//        try {
//            System.out.println("1111");
//        } catch (Exception e) {
//            // TODO: handle exception
//        } finally {
//            lock.unlock();
//        }
//    }
    public static void main(String[] args) {
        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = false;
        boolean b4 = false;


        System.out.println(b1 == b2);
        System.out.println(b2 == b3);
        System.out.println(b3 == b4);
        System.out.println(b1 == b4);

        System.out.println(b1 ^ b2);
        System.out.println(b3 ^ b4);
        System.out.println(b1 ^ b3);
        System.out.println(b2 ^ b4);
    }
}