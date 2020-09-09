package com.cjt.concurrency7;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-11 01:10
 */
public class MyTest1 {

  //Callable是有返回结果
  //Runnable没有返回结果
  public static void main(String[] args) {

    Callable<Integer> callable = () -> {

      System.out.println("进入 callable...");

      Thread.sleep(5000);

      int random = new Random().nextInt(500);

      System.out.println("执行 callable...");

      return random;
    };

    FutureTask<Integer> futureTask = new FutureTask<>(callable);

    new Thread(futureTask).start();

    System.out.println("线程 start...");

    try {
      Thread.sleep(2000);
      System.out.println("结果:" + futureTask.get());
      //System.out.println(futureTask.get(6, TimeUnit.SECONDS));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
