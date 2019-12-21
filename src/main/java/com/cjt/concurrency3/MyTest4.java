package com.cjt.concurrency3;

/**
 * 编译器对于锁的优化措施:
 *
 * 锁消除技术
 * JIT编译器(Just In Time编译器)可以在动态编译同步代码时,使用一种叫做逃逸分析的技术,来通过该项技术判别程序中所使用
 * 的锁对象是否只被一个线程所使用,而没有散布到其他线程当中;如果情况就是这样的话，那么JIT编辑器在编译这个同步代码时就
 * 不会生成synchronized关键字所标识的锁的申请与释放机器码，从而消除了锁的使用流程。
 */
public class MyTest4 {

    //若将此行移到方法内有何差别
    //private Object object = new Object();

    public void test() {
        Object object = new Object(); //此代码在方法体内,同步无意义. 字节码生成是是还会出现monitor

        //此例在真正执行时 synchronized 相当于不存在
        synchronized (object) {
            System.out.println("hello world");
        }
    }

}

