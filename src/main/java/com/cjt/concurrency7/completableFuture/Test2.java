package com.cjt.concurrency7.completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.xml.transform.Source;
import org.junit.Test;

/**
 2、计算结果完成时的回调方法

 当CompletableFuture的计算结果完成，或者抛出异常的时候，可以执行特定的Action。主要是下面的方法：

 public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
 public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
 public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
 public CompletableFuture<T> exceptionally(Function<Throwable,? extends T> fn)

 可以看到Action的类型是BiConsumer<? super T,? super Throwable>它可以处理正常的计算结果，或者异常情况。

 whenComplete 和 whenCompleteAsync 的区别：
 whenComplete：是执行当前任务的线程结束后继续执行 whenComplete 的任务。
 whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行。

 */
public class Test2 {

  @Test
  public void whenComplete() throws Exception {
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
      }
      int a = new Random().nextInt(10);
      System.out.println("a: " + a);
      if (a > 5) {
        int i = 12 / 0;
      }
      System.out.println("run end ...");
    });

    future.whenComplete(new BiConsumer<Void, Throwable>() {
      @Override
      public void accept(Void t, Throwable action) {
        System.out.println("执行完成！");
      }

    });
    future.exceptionally(new Function<Throwable, Void>() {
      @Override
      public Void apply(Throwable t) {
        System.out.println("执行失败！"+t.getMessage());
        return null;
      }
    });

    TimeUnit.SECONDS.sleep(2);
  }


  @Test
  public void whenComplete2() throws Exception {
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
      }
      int a = new Random().nextInt(10);
      System.out.println("a: " + a);
      if (a > 5) {
        int i = 12 / 0;
      }
      System.out.println("run end ...");
    });

    future.whenComplete((t, action) -> {
      System.out.println("执行完成！");
    });
    future.exceptionally(t -> {
      System.out.println("执行失败！"+t.getMessage());
      return null;
    });

    TimeUnit.SECONDS.sleep(2);
  }

}
