package com.cjt.concurrency1;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 10:11
 */
public class MyObject {

    private int counter;


    public synchronized void increase() {
        //if (counter != 0) {  //使用if会出错
        while (counter != 0) { //被唤醒后还需要检查原来条件是否满足,才能继续执行
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter ++;
        System.out.println(counter);
        notify();
    }


    public synchronized void decrease() {
        //if (counter == 0) {
        while (counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter --;
        System.out.println(counter);
        notify();
    }

}

