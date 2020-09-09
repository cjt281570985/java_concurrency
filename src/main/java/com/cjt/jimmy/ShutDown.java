package com.cjt.jimmy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-07-06 21:01
 */
public class ShutDown {

  public static void main(String[] args) throws Exception {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 10; i++) {
      executorService.execute(new ShutDownTask());
    }

    Thread.sleep(2000);
    System.out.println(executorService.isShutdown());
    System.out.println(executorService.isTerminated());
    executorService.shutdown();
    System.out.println("------");
    Thread.sleep(10000);
    System.out.println(executorService.isShutdown());
    System.out.println(executorService.isTerminated());
    executorService.execute(new ShutDownTask());
  }

}

class ShutDownTask implements Runnable {

  @Override
  public void run() {
    try {
      Thread.sleep(500);
      System.out.println(Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}