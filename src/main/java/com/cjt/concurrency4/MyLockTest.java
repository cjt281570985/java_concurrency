package com.cjt.concurrency4;

/**
 * @Author: chenjt
 * @Description: 测试可重入锁
 * @Date: Created 2020-05-08 17:13
 */
public class MyLockTest {

  private MyLock lock = new MyLock();

  public void method1() {
    lock.lock();
    System.out.println("method1.....");
    method2();
    lock.unlock();
  }

  public void method2() {
    lock.lock();
    System.out.println("method2.....");
    lock.unlock();
  }

  public static void main(String[] args) {

      MyLockTest  myLockTest = new MyLockTest();
      new Thread(() -> myLockTest.method1()).start();


  }

}
