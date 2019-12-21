package com.cjt.concurrency2;

/**
 * @Author: chenjt
 * @Description: 通过2个实例,来说明synchronized关键字
 * @Date: Created 2019-09-14 13:25
 */
public class MyThreadTest2 {

    public static void main(String[] args) {

        MyClass myClass = new MyClass();
        MyClass myClass2 = new MyClass();
        Thread thread1 = new Thread1(myClass);
        Thread thread2 = new Thread2(myClass2);

        thread1.start();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();
    }

}

class MyClass { //同一个对象的synchronized方法, 是同一个锁

    //如果此处添加了static 则 获取到的锁是指这个类的锁, 没加static是则获取的锁是对象
    public synchronized void hello() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }

    public synchronized void world() {
        System.out.println("world");
    }
}

class Thread1 extends Thread {
    private MyClass myClass;

    public Thread1(MyClass myClass) {
        this.myClass = myClass;
    }

    @Override
    public void run() {
        myClass.hello();
    }
}

class Thread2 extends Thread {
    private MyClass myClass;

    public Thread2(MyClass myClass) {
        this.myClass = myClass;
    }

    @Override
    public void run() {
        myClass.world();
    }
}