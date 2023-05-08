package com.cjt;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: jimmy
 * @Description:
 * @Date: Created 2023-05-04 下午3:12
 */
public class TT {

  public static void main(String[] args) throws Exception {

    CompletableFuture.supplyAsync(() -> {

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "5";
    }).whenComplete((t, action) -> {

      System.out.println("rs: " + t);
    });

    System.out.println("end");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }



    CompletableFuture.supplyAsync(() -> {

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "22";
    }).thenAcceptBothAsync(CompletableFuture.supplyAsync(() -> {

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "33";
    }), (a,b) -> {
      System.out.println(a+"!!!!"+b);
    });

    System.out.println("end");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }



}

