package com.cjt.concurrency1;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 10:15
 */
public class MyTestClint1 {

    public static void main(String[] args) {

        MyObject myObject = new MyObject();

        Thread increaseThread = new IncreaseThread(myObject);
        Thread increaseThread2 = new IncreaseThread(myObject);
        Thread decreaseThread = new DecreaseThread(myObject);
        Thread decreaseThread2 = new DecreaseThread(myObject);

        increaseThread.start();
        increaseThread2.start();
        decreaseThread.start();
        decreaseThread2.start();

    }

}

