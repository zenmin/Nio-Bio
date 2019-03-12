import java.util.concurrent.CountDownLatch;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/12 20:21
 * <p>
 * CountDownLatch : 闭锁  当某些运算执行完成后  当前线程再执行
 */
public class Demo03CountDownLatch {

    public static void main(String args[]) {

        final CountDownLatch countDownLatch = new CountDownLatch(10);       // 底层维护10 这个变量

        CountDown countDown = new CountDown(countDownLatch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++)
            new Thread(countDown).start();

        try {
            countDownLatch.await();         // 主线程等待  直到变量为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("============" + (end - start));

    }


}

class CountDown implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDown(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0)
                System.out.println(i);
        }

        countDownLatch.countDown();     // countDownLatch底层维护一个我们传入的变量  当这个线程执行完成后  这个变量减一  直到为0
    }
}