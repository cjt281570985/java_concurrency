package com.cjt.concurrency2;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 13:25
 */
public class MyThreadTest {

    public static void main(String[] args) {
        Runnable r = new MyThread();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);

        thread1.start();
        thread2.start();
    }

}


class MyThread implements Runnable {

    int x;
    @Override
    public void run() {
        x = 0;
        while (true) {
            System.out.println(x++);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x == 20) {
                break;
            }
        }
    }
}