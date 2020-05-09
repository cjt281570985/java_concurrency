package com.cjt.concurrency2;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-05-01 14:33
 */
public class Thread3 extends Thread {
  private MyStu myClass;

  public Thread3(MyStu myClass) {
    this.myClass = myClass;
  }

  @Override
  public void run() {
    myClass.method1();
  }
}
