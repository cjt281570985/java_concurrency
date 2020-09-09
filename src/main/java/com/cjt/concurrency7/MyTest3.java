package com.cjt.concurrency7;

/**
 ThreadLocal

 本质上，ThreadLocal是通过空间来换取时间，从而实现每个线程当中都会有一个变量的副本，这样每个线程就都会操作该副本,从而完全规避了多线程的并发问题。

 Java中存在4种类型引用
 1.强引用(strong)   如果一个对象被强停止引用所指向, 它不会被垃圾收集器回收
 2.软引用(soft)  当内存空间明显不够的情况,GC才会将软引用所指向对象回收
 3.弱引用(weak) 在下一次垃圾回收的情况下被回收
 4.虚引用(phantom)

 除了强引用用外, 其它要继承Reference

 重要:***
 ThreadLocal中 Entry extends WeakReference 防止内存泄露 (继承WeakReference后, 若代码写得不正确也可能千万内存泄露)

 栈: 引用在此上, 局部变量引用
 堆: new出来的对象

 */
public class MyTest3 {

  public static void main(String[] args) throws Exception {

    ThreadLocal<String> threadLocal = new ThreadLocal();

    threadLocal.set("hello");
    String s = threadLocal.get();
    System.out.println(s); //hello

    Thread thread = new Thread(() -> {
      System.out.println("---------------11111-----------------");
      threadLocal.set("666");
      System.out.println(threadLocal.get());
      System.out.println("---------------11111-----------------");
    });

    thread.start();
    threadLocal.set("jimmy");

    System.out.println(threadLocal.get()); //jimmy

    System.out.println("--------------------------------");

    System.out.println(threadLocal.get());
    //System.out.println(threadLocal);

    Thread.sleep(60000);
  }

}
