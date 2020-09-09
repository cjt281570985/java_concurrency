package com.cjt.concurrency9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
   int corePoolSize: 线程池当中所-直维护的线程数量，如果线程池处于任务空闲期间，那么该线程也并不会被回收掉
   int maximumPoolSize: 线程池中所维护的线程数的最大数量
   long keepAliveTime: 超过了corePoolSize的线程在经过keepAliveTime时间后如果一直处于空闲状态,那么这部分线程会被回收
   TimeUnit unit: keepAliveTime的时间单位
   BlockingQueue<Runnable> workQueue: 向线程池所提交的任务位于的阻塞队列，它的实现有多种方式
   ThreadFactory threadFactory: 线程工厂，用于创建新的线程并被线程池所管理，默认线程工厂所创建的线程都是用户线程且优先级为正常优先级
   RejectedExecutionHandler handler: 表示当线程池中的线程都在忙于执行任务且阻塞队列也已经满了的情况下，新到来的任务该如何被对待和处理。
   它有四种实现策略:

   AbortPolicy: 直接抛出运行期异常
   DiscardPolicy: 丢掉任务, 空执行
   DiscardOldestPolicy: 将丢弃最早的未处理请求，然后重试执行， 并且为当前所提交的任务留出一个队列的空闲空间.除非执行器被关闭，在这种情况下，该任务将被丢弃。
   CallerRunsPolicy: 直接由提交任务的线程来运行这个提交的任务，除非执行器已关闭，在这种情况下，该任务将被丢弃

   在线程池中,最好将偏向锁的标志关闭
 */
public class MyTest1 {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    //ExecutorService executorService = Executors.newSingleThreadExecutor();

    IntStream.range(0, 10).forEach(k -> {
      executorService.submit(() -> {
        IntStream.range(0, 50).forEach(i -> {
          System.out.println(Thread.currentThread().getName());
        });
      });
    });

    executorService.shutdown();
  }

}
