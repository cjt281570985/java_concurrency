package com.cjt.concurrency7.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 1、 runAsync 和 supplyAsync方法

 CompletableFuture 提供了四个静态方法来创建一个异步操作。

 public static CompletableFuture<Void> runAsync(Runnable runnable)
 public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
 public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)

 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。

 runAsync方法不支持返回值。
 supplyAsync可以支持返回值。
 */
public class Test1 {

  //无返回值
  @Test
  public void runAsync() throws Exception {
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
      }
      System.out.println("run end ...");
    });

    System.out.println("11111");
    future.get();
    System.out.println("主线程结束");
  }

  //有返回值
  @Test
  public void supplyAsync() throws Exception {
    CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
      }
      System.out.println("run end ...");
      return System.currentTimeMillis();
    });

    long time = future.get();
    System.out.println("time = "+time);
    System.out.println("主线程结束");
  }

}
