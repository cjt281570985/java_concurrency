package com.cjt.concurrency2;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-01 14:34
 */
public class Thread4 extends Thread {
  private MyStu myClass;

  public Thread4(MyStu myClass) {
    this.myClass = myClass;
  }

  @Override
  public void run() {
    myClass.method3();
  }
}