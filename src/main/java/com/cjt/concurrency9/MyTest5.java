package com.cjt.concurrency9;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Author: jimmy
 * @Description:
 * @Date: Created 2023-05-15 下午2:55
 */
public class MyTest5 {

  public static void main(String[] args) {

    Random random = new Random();

    IntStream.range((0), 10).forEach(i -> {
      System.out.println(random.nextInt(10));
    });
  }

}
