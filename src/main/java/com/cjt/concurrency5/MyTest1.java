package com.cjt.concurrency5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * CountDownLatch
 */
public class MyTest1 {

  public static void main(String[] args) {

    //计数器变为0后就永远为0
    CountDownLatch countDownLatch = new CountDownLatch(5);

    IntStream.range(0,3).forEach(i -> new Thread(() -> {
      try {
        Thread.sleep(2000);
        System.out.println("hello");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        //计数器变为0后就永远为0
        countDownLatch.countDown();
      }
    }).start());

    System.out.println("启动子线程完毕");

    try {
      //countDownLatch.await();
      countDownLatch.await(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("主线程执行结束");

  }

}
