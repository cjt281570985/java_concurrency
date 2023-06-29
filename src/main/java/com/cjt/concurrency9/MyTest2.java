package com.cjt.concurrency9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 AbortPolicy: 直接抛出运行期异常
 DiscardPolicy: 丢掉任务, 空执行
 DiscardOldestPolicy: 将丢弃最早的未处理请求，然后重试执行， 并且为当前所提交的任务留出一个队列的空闲空间.除非执行器被关闭，在这种情况下，该任务将被丢弃。
 CallerRunsPolicy: 直接由提交任务的线程来运行这个提交的任务，除非执行器已关闭，在这种情况下，该任务将被丢弃
 */
public class MyTest2 {

  /*
  对于线程池来说，其提供了execute与submit两种方式来向线程池提交任务
  总体来说，submit方法是可以取代execute方法的，因为它既可以接收Callable任务,也可以接收Runnable任务。

  关于线程池的总体执行策略
  1.如果线程池中正在执行的线程数 < corePoolsize, 那么线程池就会优先选择创建新的线程而非将提交的任务加到阻塞队列中。
  2.如果线程池中正在执行的线程数 >= corePoolSize, 那么线程池就会优先选择对提交的任务进行阻塞排队而非创建新的线程。
  3.如果提交的任务无法加入阻塞队列中,那么线程池就会创建新的线程; 如果创建线程数超过了maximumPoolSize, 那么拒绝策略就会起作用.

  关于线程池任务提交总结:
  1: 2种提交方式: submit, execute
  2: 当Callable对象构造后,最终都会调用Executor接口中声明的execute方法进行统一处理.

  对于线程池来说,存在2个状态需要维护:
  1: 线程池本身的状态: ctl的高3位来表示
  2: 线程池中所运行着的线程的数量: ctl的29位

  线程池存在5种状态:
  RUNNING: 线程池可以接收新的任务提交,并且可以正常处理阻塞队列中的任务.
  SHUTDOWN: 不再接收新的任务提交,不过线程池可以继续处理阻塞队列中的任务.
  STOP: 不再接收新的任务,同时还会丢弃阻塞队列中的任务;此处还会中断正在处理中的任务.
  TIDYING: 所有的任务都执行完后(同进也涵盖了阻塞队列中的任务),当前线程池中的活动的线程数量降为0, 将会调用terminated方法.
  TERMINATED: 线程池的终止状态, 当terminated方法执行完后,线程池将处理该状态之下.

  59.
  RUNNING -> SHUTDOWNT: 当調用了线程池的shutdown方法吋，或者当finalize方法被隠式调用后(核方法内部会调用shutdown方法)
  RUNNING, SHUTDOWNT > STOP: 当調用了线程池的shutdownNow方法时
  SHUTDOWN TIDYING:在线程池与阻塞从列均变为空时
  STOP -> TIDYING: 在线程池变为空时
  TIDYING -> TERMINATED: 在terminated方法被抗行完毕时

   */
  public static void main(String[] args) {

    ExecutorService executorService = new ThreadPoolExecutor(3, 5, 0, TimeUnit.SECONDS,
        new LinkedBlockingQueue(3), new ThreadPoolExecutor.CallerRunsPolicy());

    //此例子9个线程,而线程池只能最大是5个, 等待队列最多是3个, 所以最多能执行8个
    //当拒绝策略是 AbortPolicy 时, 会抛异常
    //当拒绝策略是 DiscardPolicy 时, 会丢掉任务, 空执行
    //当拒绝策略是 DiscardOldestPolicy 时, 会丢弃最早的未处理请求, 执行新提交的任务
    //当拒绝策略是 CallerRunsPolicy 时, 多出来的任务会由main线程来执行

    IntStream.range(0,9).forEach(i -> {
      executorService.submit(() -> {

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        IntStream.range(0,1).forEach(j -> {
          System.out.println(Thread.currentThread().getName());
        });
      });
    });

    executorService.shutdown();
  }



}
