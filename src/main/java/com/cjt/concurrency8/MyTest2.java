package com.cjt.concurrency8;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
   关于ReentrantReaawriterock的操作逻辑：
   读锁：
   1．在获取读锁过，会尝试判断当前对象是否拥有了写锁，如果已经拥有，则直接失败。
   2．如果没有写锁，就表示当前对象没有排他锁，则当前线程会尝试给对象加锁
   3．如果当前线程已经持有了该对象的锁，那么直接将读锁数量加1
   写锁：
   1．在获取写锁时，会尝试判断当前对象是否拥有了锁（读锁与写锁),如果已经拥有且持有的线程并非当前线程，直接失败。
   2．如果当前对象没有被加锁，那么写锁就会为当前对象上锁，并且将写锁的个数加1
   3．将当前对象的排他锁线程持有者设为自己
 */
public class MyTest2 {

  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  public void test() {
    readWriteLock.readLock().lock();

    try {
      Thread.sleep(1000);

      System.out.println(2222);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  public static void main(String[] args) {

      MyTest2 myTest2 = new MyTest2();

    IntStream.range(0,10).forEach(i -> {
      new Thread(myTest2::test).start();
    });


  }

}
