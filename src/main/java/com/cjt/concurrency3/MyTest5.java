package com.cjt.concurrency3;

/**
 * 锁粗化
 *
 * JIT编译器在执行动态编译时,若发现前后相邻的synchronized块的是同一锁对象,那么它就会把这几个synchronized块给合并成一个较大的同步块,
 * 这样做的好处在于线程在执行代码时就无需频繁申请写释放锁了,从而达到申请与释放锁一次,就可以执行完全部的同步代码块,从而提升了性能
 */
public class MyTest5 {

    private Object object = new Object();

    public void test() {

        synchronized (object) {
            System.out.println("hello world");
        }

        synchronized (object) {
            System.out.println("hello cjt");
        }

        synchronized (object) {
            System.out.println("hello jimmy");
        }
    }
}

