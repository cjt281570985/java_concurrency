package com.cjt.concurrency9;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-07-05 10:35
 */
public class MyTest4 {

  public static void main(String[] args) throws Exception {

    ExecutorService executorService = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(20), new AbortPolicy());

    CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

    IntStream.range(0, 10).forEach(i -> {
      completionService.submit(() -> {
        Thread.sleep((long) (Math.random() * 1000));

        System.out.println(Thread.currentThread().getName());

        return i;
      });
    });

    for (int i = 0; i < 10; i++) {
      int result = completionService.take().get();
      System.out.println(result);
    }

    executorService.shutdown();
  }

}
