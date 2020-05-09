package com.cjt.concurrency2;

import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-01 14:15
 */
public class MyStu {

  public synchronized void method1() {
    try {
      System.out.println("111");
      TimeUnit.SECONDS.sleep(3);
    } catch (Exception e){

    }
    System.out.println("method1");
  }

  public synchronized void method2() {
    try {
      System.out.println("222");
      TimeUnit.SECONDS.sleep(1);
    } catch (Exception e){

    }
    System.out.println("method2");
  }

  public synchronized static void method3() {
    try {
      System.out.println("333");
      TimeUnit.SECONDS.sleep(1);
    } catch (Exception e){

    }
    System.out.println("method3");
  }

  public synchronized static void method4() {
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (Exception e){

    }
    System.out.println("method4");
  }

  public static void main(String[] args) {

    MyStu myClass = new MyStu();
    MyStu myClass2 = new MyStu();
    Thread3 thread1 = new Thread3(myClass);
    Thread4 thread2 = new Thread4(myClass);

    thread1.start();

    try {
      Thread.sleep(700);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    thread2.start();
  }





}