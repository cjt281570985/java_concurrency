package com.cjt.concurrency3;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 22:00
 */
public class MyTest1 {

    //当我们使用synchronized关键字来修饰代码块时,字节码层面上是通过monitorenter与monitorexit指令来实现的锁的获取与释放动作。
    //当线程进入到monitorenter指令后,线程将会持有Monitor对象,退出monitorenter指令后,线程将会释放Monitor对象

    private Object object = new Object();

    public void method() throws Exception {
        synchronized (object) {
            System.out.println("hello world");
            throw new Exception();
        }
    }

    public void method2() {
        synchronized (object) {
            System.out.println("chenjt");
        }
    }

}

