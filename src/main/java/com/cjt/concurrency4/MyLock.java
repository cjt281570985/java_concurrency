package com.cjt.concurrency4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-08 17:10
 */
public class MyLock implements Lock {

  Thread lockBy = null;
  int lockCount = 0;
  //锁标志
  private boolean isLocked = false;

  @Override
  public void lock() {
    Thread currentThread = Thread.currentThread();

    //如果不是第一个进来，则在这儿等待
    while (isLocked && currentThread != lockBy) {
      // ...阻塞到这儿,都等待，但是第一个进来不让让等待
      try {
        wait();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    //如果是第一个进来，将其变为false
    isLocked = true;
    //将lockBy指向当前线程
    lockBy = currentThread;
    //计数器自增
    lockCount++;
  }


  @Override
  public void unlock() {
  //如果当前线程等于lockBy
    if (lockBy == Thread.currentThread()) {
      lockCount--;
      if (lockCount == 0) {
        notify();
        //释放锁
        isLocked = false;
      }
    }
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {

  }

  @Override
  public boolean tryLock() {
    return false;
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return false;
  }


  @Override
  public Condition newCondition() {
    return null;
  }
}
