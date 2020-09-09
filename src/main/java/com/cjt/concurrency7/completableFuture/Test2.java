package com.cjt.concurrency7.completableFuture;

import java.util.Objects;
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


  /**
   * thenApply 方法
   * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
   *
   * Function<? super T,? extends U>
   * T：上一个任务返回结果的类型
   * U：当前任务的返回值类型
   */
  @Test
  public void thenApply() throws Exception {
    //第二个任务依赖第一个任务的结果
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5).thenApply((m -> m + 2));

    Integer rs = future.get();

    System.out.println(rs);
  }

  /**
   * handle 方法
   * handle 是执行任务完成时对结果的处理。
   * handle 方法和 thenApply 方法处理方式基本一样。不同的是 handle 是在任务完成后再执行，还可以处理异常的任务。
   * thenApply 只可以执行正常的任务，任务出现异常则不执行 thenApply 方法。
   *
   * public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
   * public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);
   * public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn,Executor executor);
   */
  @Test
  public void handle() throws Exception {
    //从示例中可以看出，在 handle 中可以根据任务是否有异常来进行做相应的后续处理操作。
    //而 thenApply 方法，如果上个任务出现错误，则不会执行 thenApply 方法。
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 2/0).handle((m, throwable) -> {
      int result = -1;

      if (Objects.isNull(throwable)) {
        result = m + 5;
      } else {
        System.out.println(throwable.getMessage());
      }

      return result;
    });

    System.out.println(future.get());

  }


  /**
   * 接收任务的处理结果，并消费处理，无返回结果
   * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
   * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
   * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
   */
  @Test
  public void thenAccept() throws Exception {

    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> 5).thenAccept(val -> {
      System.out.println(val + 10);
    });

    //thenAccept结果消费了, 此处无返回结果
    System.out.println(future.get()); //null
  }

  @Test
  public void thenRun() throws Exception {

    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> 5).thenRun(() -> {
      System.out.println("thenRun");
    });

    System.out.println(future.get()); //null
  }

  /**
   * thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理
   * public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
   * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
   * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
   */
  @Test
  public void thenCombine() throws Exception {

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 3);
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 5);

    CompletableFuture<Integer> combine = f1.thenCombine(f2, Integer::sum);
    System.out.println(combine.get()); //8
  }

  /**
   * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
   * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
   * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
   */
  @Test
  public void thenAcceptBoth() {

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 3);
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 5);

    f1.thenAcceptBoth(f2, (a, b) -> {
      System.out.println(a * b); //15
    });
  }

}
