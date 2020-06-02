package com.cjt.concurrency7;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-19 00:10
 */
public class MyTest2 {

  public static void main(String[] args) {

    //任务分阶段执行及处理
    String result = CompletableFuture.supplyAsync(() -> "hello").thenApplyAsync(value -> value + " world").join();
    System.out.println(result); //hello world

    System.out.println("--------------------------------");

    CompletableFuture.supplyAsync(() -> "hello").thenAccept(value -> System.out.println("jimmy " + value)); //jimmy hello

    System.out.println("--------------------------------");

    //2个异步任务结果合并处理
    String result2 = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "你好";
    }).thenCombine(CompletableFuture.supplyAsync(() -> {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "并发";
    }), (s1, s2) -> s1 + " " + s2).join();

    System.out.println(result2); //你好 并发

    System.out.println("------------异步获取任务结果--------------------");

    //任务执行的异步性, 获取结果的异步性
    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("task finished");
    });

    //异步获得执行结果
    completableFuture.whenComplete((t, action) -> System.out.println("执行完成"));

    System.out.println("主线程执行结束");

    try {
      Thread.sleep(7000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }


}
