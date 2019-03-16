import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/16 21:10
 *
 * 处理线程安全方式
 * 同步代码块 synchronized
 * 同步方法块 synchronized
 * lock
 */
public class Demo05Lock {

    public static void main(String args[]) {

        LockDemo lockDemo = new LockDemo();

        new Thread(lockDemo, "线程1").start();

        new Thread(lockDemo, "线程2").start();

        new Thread(lockDemo, "线程3").start();
    }


}

class LockDemo implements Runnable {

    private Integer ticket = 1000;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                Thread.sleep(100);
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ":剩余 " + --ticket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}