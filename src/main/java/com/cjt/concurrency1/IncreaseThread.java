package com.cjt.concurrency1;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 10:16
 */
public class IncreaseThread extends Thread {

    private MyObject myObject;

    public IncreaseThread(MyObject myObject) {
        this.myObject = myObject;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myObject.increase();
        }
    }
}

