package com.cjt;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class T {

  public static void main(String[] args) throws InterruptedException {


    CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    for (int k = 0; k < 2; k++) {
      System.out.println("------------------------------------------------------------------------------------------------");
      IntStream.range(0,3).forEach(i -> {

        new Thread(()->{
          try {
            Thread.sleep((long) (Math.random() * 2000));
            System.out.println("hello - " + i);

            cyclicBarrier.await();
            System.out.println("end - " + i);
          } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
          } finally {

          }
        }).start();
      });
    }

    System.out.println("--------------------------------");



    System.out.println("--------------主线程结束------------------");

  }


}