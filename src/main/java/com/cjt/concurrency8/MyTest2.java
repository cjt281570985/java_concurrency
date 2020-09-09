package com.cjt.concurrency8;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**

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
