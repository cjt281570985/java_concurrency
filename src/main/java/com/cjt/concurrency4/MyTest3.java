package com.cjt.concurrency4;

/**
 volatile关键字
 private volatile int count;

 volatile关键字主要有三方面作用:
 1.实现 long/double类型变量的原子操作
 2.防止指令重排序
 3.实现变量的可见性

 volatile double a = 1.0
 例子: 比如修改某个数的低32位,另一个线程修改高32位, 第3个线程读取错误

 当使用volatile修饰变量时，应用就不会从寄存器中获取该变量的值，而是从内存(高速缓存)中获取。

 volatile与锁类似的地方有两点:
 1.确保变量的内存可见性
 2.防止指令重排序

 volatile可以确保对变量写操作的原子性，但不具备排他性
 原子性: 指一条CPU指令
 排他性: 只有一个线程能进入, 其它线程进入不了,需要等当前线程执行完才能进入

 另外的重要一点在于:使用锁可能会导致线程的上下文切换(内核态与用户态之间的切换)，但使用volatile并不会出现这种情况

 volatile int count = 1;
 volatile boolean flag = false;

 如果要实现volatile写操作的原子性,那么在等号右侧的赋值变量中就不能出现被多线程所共享的变量，哪怕这个变量也是个volatile也不可以。
 int a = b + 2; //不适用, 右边有变量b 它无法保证原子性(需要先读取变量b,再加2)

 volatile Date date = new Date();

 ----------------------------------------------------------------------------------

 防止指令重排序与实现变量的可见性都是通过-种手段来实现的:内存屏障(memory barrier)

 int a= 1;
 String s = "hello";

 内存屏障(Release Barrier,释放屏障)

 volatile boolean v = false; // 写入操作

 内存屏障(Store Barrier, 存储屏障)

 Release Barrier: 防止下面的volatile与上面的所有操作的指令重排序。(在volatile操作前将上面的指令执行后释放出去)
 Store Barrier: 重要作用是刷新处理器缓存，结果是可以确保该存储屏障之前一切的操作所生成的结果对于其他处理器来说都可见。

 内存屏障(Load Barrier, 加载屏障)

 boolean v1 = V; //读取

 内存屏障(Acquire Barrier, 获取屏障)

 int a= 1;
 String s = "hello";
`
 Load Barrier: 可以刷新处理器缓存，同步其他处理器对该volatile变量的修改结果。
 Acquire Barrier: 可以防止"上面"的volatile读取操作与下面的所有操作语句的指令重排序。

 对于volatile关键字变量的读写操作，本质上都是通过内存屏障来执行的。
 内存屏障兼具了两方面能力: 1.防止指令重排序，2. 实现变量内存的可见性。

 1.对于读取操作来说，volatile可以确保该操作与其"后面"的所有读写操作都不会进行指令重排序
 2.对于修改操作来说，volatile可以确保该操作与其"上面"的所有读写操作都不会进行指令重排序

 以上介绍的volatile主要是针对原生类型的操作, 对引用类型是不适用的
 ///////////////////////////////

 ArrayList

 ****************************************************************************************
 volatile 与 锁 的一些比较  (面试)

 锁更强大  锁还具有排它性
 锁同样具备变量内存可见性与防止指令重排序的功能。
 monitorenter
 内存屏障(Acquire Barrier, 获取屏障)

 ..............................

 内存屏障(Release Barrier,释放屏障)
 monitorexit

 */
public class MyTest3 {

}
