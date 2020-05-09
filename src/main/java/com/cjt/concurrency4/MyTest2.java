package com.cjt.concurrency4;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 传统上，我们可以通过synchronized关键字 + wait + notify/notifyAll 来实现多个线程之间的协调与通信，整个过程都是由JVM来帮助
 * 我们实现的;开发者无需(也是无法)了 解底层的实现细节
 *
 * 从JDK 5开始，并发包提供了Lock, Condition(await与signal/signalA11 )来实现多个线程之间的协调与通信，整个过程都是由开发者来
 * 控制的，而且相比于传统方式，更加灵活，功能也更加强大
 *
 * Thread. sleep与await. (或是0bject的wait方法) 的本质区别: sleep方法本质.上不会释放锁，而await会释放锁， 并且在signal后，还需要
 * 重新获得锁才能继续执行(该行为与object的wait方法完全一致)
 */
public class MyTest2 {

  public static void main(String[] args) {

    BoundedContainer bc = new BoundedContainer();

    IntStream.range(0, 10).forEach(i -> new Thread(() -> {
      try {
        bc.take();
      } catch (Exception e) {

      }
    }).start());

    IntStream.range(0, 10).forEach(i -> new Thread(() -> {
      try {
        bc.put("hello");
      } catch (Exception e) {

      }
    }).start());

  }
}

class  BoundedContainer {

  private String[] elements = new String[10];

  private Lock lock = new ReentrantLock();

  private Condition notEmptyCondition = lock.newCondition();

  private Condition notFullCondition = lock.newCondition();

  private int elementCount; //已有的元素数量

  private int putIndex;

  private int takeIndex;


  public void put(String ele) throws InterruptedException {
    this.lock.lock();
    try {
      //如果所有位置已放满
      while (elementCount == elements.length) {
        notFullCondition.await();
      }

      this.elements[putIndex] = ele;
      if (++putIndex == elements.length) {
        putIndex = 0;
      }
      elementCount++;
      System.out.println("put: " + Arrays.toString(elements));
      notEmptyCondition.signal();
    } finally {
      this.lock.unlock();
    }
  }

  public void take() throws InterruptedException {
    this.lock.lock();
    try {
      while (elementCount == 0) {
        notEmptyCondition.await();
      }

      this.elements[takeIndex] = null;
      elementCount--;

      if (++takeIndex == elements.length) {
        takeIndex = 0;
      }
      System.out.println("take: " + Arrays.toString(elements));
      notFullCondition.signal();
    } finally {
      this.lock.unlock();
    }
  }


}
