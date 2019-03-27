import sun.misc.ConditionLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/27 21:46
 * <p>
 * Condition 线程通信   await() signalAll()  signal()
 * <p>
 * 开启三个线程 让三个线程一次打印自己的ID  如 ABC ABC ABC
 */
public class Demo06Condition {

    public static void main(String args[]) {

        ConditionDemo conditionDemo = new ConditionDemo();

        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                conditionDemo.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                conditionDemo.loopB(i);
            }
        }, "B").start();


        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                conditionDemo.loopC(i);
            }
        }, "C").start();

    }

    static class ConditionDemo {

        private int num = 1;

        private Lock lock = new ReentrantLock();

        Condition conditionLock1 = lock.newCondition();

        Condition conditionLock2 = lock.newCondition();

        Condition conditionLock3 = lock.newCondition();

        // total循环第几轮
        public void loopA(int total) {
            lock.lock();
            try {
                // 判断是否是第一个线程
                if (num != 1) {
                    conditionLock1.await();
                } else {
                    // 打印
                    for (int i = 1; i <= 5; i++) {
                        System.out.println(Thread.currentThread().getName() + "  " + i + "  " + total);
                    }

                    // 唤醒第二个线程
                    num = 2;
                    conditionLock2.signalAll(); // 使用notifyAll() 会抛出IllegalMonitorStateException异常  不是当前线程拥有者
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }

        // total循环第几轮
        public void loopB(int total) {
            lock.lock();
            try {
                // 判断是否是第一个线程
                if (num != 2) {
                    conditionLock2.await();
                } else {
                    // 打印
                    for (int i = 1; i <= 15; i++) {
                        System.out.println(Thread.currentThread().getName() + "  " + i + "  " + total);
                    }

                    // 唤醒第3个线程
                    num = 3;
                    conditionLock3.signalAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }


        // total循环第几轮
        public void loopC(int total) {
            lock.lock();
            try {
                // 判断是否是第一个线程
                if (num != 3) {
                    conditionLock3.await();
                } else {
                    // 打印
                    for (int i = 1; i <= 25; i++) {
                        System.out.println(Thread.currentThread().getName() + "  " + i + "  " + total);
                    }

                    // 唤醒第1个线程
                    num = 1;
                    conditionLock1.signalAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }
    }


}
