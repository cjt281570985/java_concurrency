package com.cjt.concurrency5;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 关于CyclicBarrier的底层执行流程

 1.初始化cyclicBarrier中的各种成员变量，包括parties、count以及Runnable (可选)
 2. 当调用await方法时，底层会先检查计数器是否已经归零，如果是的话，那么就首先执行可选的Runnable,接下来开始下一个generation;
 3.在下一个分代中，将会重置count值为parties, 并且创建新的Generation实例。
 4. 同时会调用Condition的signalAll方法，唤醒所有在屏障前面等待的线程，让其开始继续执行。
 5.如果计数器没有归零，那么当前的调用线程将会通过Condition的await方法，在屏障前进行等待。
 6.以上所有执行流程均在lock锁的控制范围内，不会出现并发情况。
 */
public class MyTest2 {

  public static void main(String[] args) throws Exception {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    for (int i = 0; i < 3; ++i) {
      new Thread(() -> {
        try {
          System.out.println("--2--");
          Thread.sleep((long) (Math.random() * 2000));
          //Thread.sleep(3000);
          int randomInt = new Random().nextInt(500);
          System.out.println("hello-" + randomInt);

          cyclicBarrier.await();

          System.out.println("end-" + randomInt);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}