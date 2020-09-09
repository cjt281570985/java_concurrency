package com.cjt.concurrency7.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;


public class Test3 {

  /**
   * 两个CompletionStage，谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的转化操作
   * public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
   * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
   * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
   */
  @Test
  public void applyToEither() throws Exception {

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 3;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 6;
    });

    CompletableFuture<Integer> future = f1.applyToEither(f2, fast -> {
      System.out.println(fast);
      return fast * 2;
    });

    //谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的转化操作
    System.out.println(future.get());
  }

  //谁执行返回的结果快，我就用那个CompletionStage的结果进行消费
  @Test
  public void acceptEither() throws Exception {

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      int t1 = 4;
      try {
        TimeUnit.SECONDS.sleep(t1);
        System.out.println("f1");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1111;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      int t2 = 3;
      try {
        TimeUnit.SECONDS.sleep(t2);
        System.out.println("f2");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return t2;
    });

    //上面的是异步操作,主线程很快结束的
    f1.acceptEither(f2, (fast -> System.out.println(fast)));

    //主要线程先暂停才能看到结果
    Thread.sleep(5000);
  }


  /**两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）
   *
   * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
   * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
   * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
   */
  @Test
  public void runAfterEither() throws Exception {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f1结束");
      return 3;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f2结束");
      return 6;
    });

    f1.runAfterEither(f2, () -> {
      System.out.println("已经有一个线程完成了");
    });

    System.out.println("主线程结束");

    Thread.sleep(5000);
  }

  /**
   * 两个CompletionStage，都完成了计算才会执行下一步的操作（Runnable）
   *
   * public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
   * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
   * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
   */
  @Test
  public void runAfterBoth() throws Exception {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(4);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f1结束");
      return 3;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f2结束");
      return 6;
    });

    f1.runAfterBothAsync(f2, () -> {
      System.out.println("2个全部完成了");
    });

    System.out.println("主线程结束");

    Thread.sleep(5000);
  }

  /**
   * thenCompose 方法允许你对两个 CompletionStage 进行流水线操作，第一个操作完成时，将其结果作为参数传递给第二个操作。
   */
  @Test
  public void thenCompose() throws Exception {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(4);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f1结束");
      return 3;
    });
    CompletableFuture<Integer> f = f1.thenCompose(new Function<Integer, CompletionStage<Integer>>() {
      @Override
      public CompletionStage<Integer> apply(Integer param) {
        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
          @Override
          public Integer get() {
            int t = param * 3;
            System.out.println("t03="+t);
            return t;
          }
        });
      }
    });

    CompletableFuture<Integer> f11 = f1.thenCompose(param -> CompletableFuture.supplyAsync(() -> {
      int t = param * 2;
      System.out.println("t02="+t);
      return t;
    }));


    System.out.println("thenCompose result : "+f.get());
    System.out.println("thenCompose result f11 : "+f11.get());
  }

}
