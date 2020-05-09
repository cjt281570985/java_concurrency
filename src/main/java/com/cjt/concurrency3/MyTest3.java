package com.cjt.concurrency3;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-09-14 22:00
 */
public class MyTest3 {
    /**
     * JVM中的同步是基于进入与退出监视器对象(管程对象)(Monitor) 来实现的,每个对象实例都会有一个Monitor对象,
     * Monitor对象会和Java对象一同创建并销毁。Monitor对象是由C++来实现的。
     *
     * 当多个线程同时访问一段同步代码时,这些线程会被放到一个EntryList集合中,处于阻塞状态的线程都会被放到该列表当中。接下来,
     * 当线程获取到对象的Monitor时,Monitor是依赖于底层操作系统的mutex lock来实现互斥的,线程获取mutex成功,则会持有该mutex,这时其他线程就无法再获取到该mutex。
     *
     * 如果线程调用了wait方法,那么该线程就会释放掉所持有的mutex,并且该线程会进入到waitSet集合(等待集合)中,
     * 等待下一次被其他线程调用notify/notifyAll唤醒。如果当前线程顺利执行完毕方法,那么它也会释放掉所持有的mutex。
     *
     * 总结一下:同步锁在这种实现方式当中,因为Monitor是依赖于底层的操作系统实现,这样就存在用户态与内核态之间的切换,
     * 所以会增加性能开销。通过对象互斥锁的概念来保证共享数据操作的完整性。每个对象都对应于一个可称为”互斥锁的标记,这个标记用于保证在任何时刻,只能有一个线程访问该对象。
     *
     * 那些处于EntryList与waitSet中的线程均处于阻塞状态,阻塞操作是由操作系统来完成的,在linux下是 通过pthread_utex_lock函数实现的。
     * 线程被阻塞后便会进入到内核调度状态,这会导致系统在用户态与内核态之间来回切换,严重影响锁的性能。
     *
     * 解决上述问题的办法便是自旋。其原理是:当发生对Monitor的争用时,若Owner能够在很短的时间内释放掉锁,则那些正在争用的线程就可以稍微等待一下(即所谓的自旋) ,
     * 在Owner线程释放锁之后,争用线程可能会立刻获取到锁,从而避免了系统阻塞。不过,当Owner运行的时间超过了临界值后,争用线程自旋一段时间后依然无法获取到锁,
     * 这时争用线程则会停止自旋而进入到阻塞状态。所以总体的思想是先自旋,不成功再进行阻塞尽量降低阻盟暴的可能型。这对那些执行时间很短的代码块来说有极大的性能提升。
     * 显然,自旋在多处理器(多核心)上才有意义。
     *
     * 互斥锁的属性:
     * 1. PTHREAD_MUTEX_TIMED_NP:这是缺省值,也就是普通锁。当一个线程加锁以后,其余请求锁的线程将会形成一个等待队列,并且在解锁后按照优先级获取到锁。
     *    这种策略可以确保资源分配的公平性
     * 2. PTHREAD_MUTEX_RECURSIVE_NP: 嵌套锁。允许一个线程对同一个锁成功获取多次,并通过unlock解锁。如果是不同线程请求,则在加锁线程解锁时重新进行竞争。
     * 3. PTHREAD_MUTEX_ERRORCHECK_NP: 检错锁。如果一个线程请求同一个锁,则返回EDEADLR, 否则与PTHREAD_MUTEX_TIMED_NP类型动作相同,
     *    这样就保证了当不允许多次加锁时不会出现最简单情况下的死锁。
     * 4. PTHREAD_MUTEX_ADAPTIVE_NP: 适应锁,动作最简单的锁类型,仅仅等待解锁后重新竞争。
     *
     * =====================================
     * 在JDK 1.5之前,我们若想实现线程同步,只能通过synchronized关键字这一种方式来达成;底层,Java也是通过synchronized关键字来做到数据的
     * 原子性维护的; synchronized关键字是JVM实现的一种内置锁,从底层角度来说,这种锁的获取与释放都是由JVM帮助我们隐式实现的。
     *
     * 从JDK 1.5开始,并发包引入了Lock锁,Lock同步锁是基于Java来实现的,因此锁的获取与释放都是通过Java代码来实现与控制的;然而,
     * synchronized是基于底层操作系统的Mutex Lock来实现的,每次对锁的获取与释放动作都会带来用户态与内核态之间的切换,
     * 这种切换会极大地增加系统的负担;在并发量较高时,也就是说锁的竞争比较激烈时,synchronized锁在性能上的表现就非常差。
     *
     * 从JDK1.6开始,synchronized锁的实现发生了很大的变化; JVM引入了相应的优化手段来提升synchronized锁的性能,这种提升涉及到偏向锁、
     * 轻量级锁及重量级锁等,从而减少锁的竞争所带来的用户态与内核态之间的切换;这种锁的优化实际上是通过Java对象头中的一些标志位来去实现的:
     * 对于锁的访问与改变,实际上都与Java对象头息息相关。
     *
     * 从JDK1.6开始,对象实例在堆当中会被划分为三个组成部分:对象头、实例数据与对齐填充字节。
     * 对象头主要也是由3块内容来构成:
     * 1.Mark word
     * 2.指向类的指针
     * 3.数组长度(只有数组对象才有)
     *
     * 其中Mark Word (它记录了对象、锁及垃圾回收相关的信息,在64位的JVM中,其长度也是64bit)的位信息包括了如下组成部分:     *
     * 1.无锁标记
     * 2.偏向锁标记
     * 3.轻量级锁标记
     * 4.重量级锁标记
     * 5.GC标记
     *
     * JVM一般是这样使用锁和Mark Word的：
     * 1，当没有被当成锁时，这就是一个普通的对象，Mark Word记录对象的HashCode，锁标志位是01，是否偏向锁那一位是0。
     *
     * 2，当对象被当做同步锁并有一个线程A抢到了锁时，锁标志位还是01，但是否偏向锁那一位改成1，前23bit记录抢到锁的线程id，表示进入偏向锁状态。
     *
     * 3，当线程A再次试图来获得锁时，JVM发现同步锁对象的标志位是01，是否偏向锁是1，也就是偏向状态，Mark Word中记录的线程id就是线程A自己的id，
     * 表示线程A已经获得了这个偏向锁，可以执行同步锁的代码。
     *
     * 4，当线程B试图获得这个锁时，JVM发现同步锁处于偏向状态，但是Mark Word中的线程id记录的不是B，那么线程B会先用CAS操作试图获得锁，这里的获得锁操作是有可能成功的，
     * 因为线程A一般不会自动释放偏向锁。如果抢锁成功，就把Mark Word里的线程id改为线程B的id，代表线程B获得了这个偏向锁，可以执行同步锁代码。如果抢锁失败，则继续执行步骤5。
     *
     * 5，偏向锁状态抢锁失败，代表当前锁有一定的竞争，偏向锁将升级为轻量级锁。JVM会在当前线程的线程栈中开辟一块单独的空间，里面保存指向对象锁Mark Word的指针，
     * 同时在对象锁Mark Word中保存指向这片空间的指针。上述两个保存操作都是CAS操作，如果保存成功，代表线程抢到了同步锁，就把Mark Word中的锁标志位改成00，
     * 可以执行同步锁代码。如果保存失败，表示抢锁失败，竞争太激烈，继续执行步骤6。
     *
     * 6，轻量级锁抢锁失败，JVM会使用自旋锁，自旋锁不是一个锁状态，只是代表不断的重试，尝试抢锁。从JDK1.7开始，自旋锁默认启用，自旋次数由JVM决定。
     * 如果抢锁成功则执行同步锁代码，如果失败则继续执行步骤7。
     *
     * 7，自旋锁重试之后如果抢锁依然失败，同步锁会升级至重量级锁，锁标志位改为10。在这个状态下，未抢到锁的线程都会被阻塞。
     *
     * 对于synchronized锁来说,锁的升级主要都是通过Mark word中的锁标志位与是否是偏向锁标志位来达成的; synchronized关键字所对应的锁都是先从偏向开始,
     * 随着锁竞争的不断升级,逐步演化至轻量级锁,最后则变成了重量级锁。
     * 对于锁的演化来说,它会经历如下阶段:
     * 无锁->偏向锁->轻量级锁->重量级锁
     *
     * 偏向锁:
     * 针对于一个线程来说的,它的主要作用就是优化同一个线程多次获取一个锁的情况;如果一个synchronized方法被一个线程访问,
     * 那么这个方法所在的对象就会在其Mark Word中的将偏向锁进行标记,同时还会有一个字段来存储该线程的ID;
     * 当这个线程再次访问同一个synchronized方法时,它会检查这个对象的Mark Word的偏向锁标记以及是否指向了其线程ID,如果是的话,
     * 那么该线程就无需再去进入管程(Monitor)了,而是直接进入到该方法体中。
     *
     * 如果是另外一个线程访问这个synchronized方法,那么实际情况会如何呢?
     * 偏向锁会被取消掉。
     *
     * 轻量级锁:
     * 若第一个线程已经获取到了当前对象的锁,这时第2个线程又开始尝试争抢该对象的锁,由于该对象的锁已经被第1个线程获取到,
     * 因此它是偏向锁,而第2个线程在争抢时,会发现该对象头中的Mark Wora已经是偏向锁,但里面存储的线程ID并不是自己(是第1个线程),
     * 那么它会进行CAS (Compare and Swap),从而获取到锁,这里面存在两种情况:
     * 1.获取锁成功:那么它会直接将Mark Word中的线程ID由第1个线程变成自己(偏向锁标记位保持不变),这样该对象依然会保持偏向锁的状态。
     * 2.获取锁失败:则表示这时可能会有多个线程同时在尝试争抢该对象的锁,那么这时偏向锁就会进行升级,升级为轻量级锁(适合2个线程轮流去访问)
     *
     * 自旋锁:
     * 若自旋失败(依然无法获取到锁),那么锁就会转化为重量级锁,在这种情况下,无法获取到锁的线程都会进入到Monitor (即内核态)
     * 自旋最大的一个特点就是避免了线程从用户态进入到内核态。
     *
     * 重量级锁:
     * 线程最终从用户态进入到了内核态。
     *
     */
    public synchronized static void method() {
        System.out.println("hello world");
    }


}

