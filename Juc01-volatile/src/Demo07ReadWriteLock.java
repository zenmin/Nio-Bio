import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/9 21:02
 * <p>
 * 读写锁
 * 写写/读写  互斥
 */
public class Demo07ReadWriteLock {

    public static void main(String args[]) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.get();
                }
            }).start();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.set(8);
            }
        }).start();
    }
}

class ReadWriteLockDemo {

    public int num = 0;

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void get() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + num);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(int num) {
        try {
            readWriteLock.writeLock().lock();
            this.num = num;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}