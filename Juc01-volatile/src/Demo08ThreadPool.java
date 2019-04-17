import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.stream.LongStream;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/16 21:34
 *
 *
 * 线程池：提供了一个线程队列，队列中保存着所有等待状态的线程  避免创建于销毁额外的开销  提高响应速度
 *
 *
 * 线程池体系结构：
 * java.util.concurrent.Executor  负责使用线程调度和使用的根接口
 *      ExecutorService 子接口  线程池的主要接口
 *          ThreadPoolExecutor 线程池的实现类
 *          ScheduledExecutorService 子接口 负责线程调度
 *              ScheduledThreadPoolExecutor 继承ThreadPoolExecutor  实现ScheduledExecutorService
 *
 *  工具类 Executors
 *  // 创建缓存线程池  大小不固定
 *   ExecutorService executorService = Executors.newCachedThreadPool();
 *
 *  // 创建一个固定大小的线程池
 *  Executors.newFixedThreadPool(10);
 *
 * // 创建单个线程池  这个池里面只有一个线程
 * Executors.newSingleThreadExecutor();
 *
 *  // 创建一个固定大小的线程池  可以延时或定时执行任务
 *  Executors.newScheduledThreadPool(10);
 *
 */
public class Demo08ThreadPool {

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        // 创建根任务
        ThreadPoolDemo t = new ThreadPoolDemo();

        ThredPoolDemo2 t2 = new ThredPoolDemo2();

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


        // 给线程池分配任务  普通Runnable
        for (int i = 0;i < 10 ;i++){
            executorService.submit(t);
        }

        // 给线程池分配任务  Callable方式
        for (int i = 0;i < 10 ;i++){
            Future<Integer> future = scheduledExecutorService.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println(Thread.currentThread().getName() );
                    return 0;
                }
            }, 3, TimeUnit.SECONDS);  // 延时调用
            System.out.println(future.get());
        }


        // 关闭线程池
        executorService.shutdown();
        scheduledExecutorService.shutdownNow();
    }

    @Test
    public void test1(){
        Instant start = Instant.now();
        // fork join  java8
        Long sum = LongStream.rangeClosed(0, 100000000000L).parallel().reduce(0L,Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();

        Duration between = Duration.between(start, end);

        System.out.println(between.toMillis());

    }


}

class ThredPoolDemo2 implements Callable{

    private int i  =0 ;
    @Override
    public Integer call() {
        System.out.println(Thread.currentThread().getName() + ": " + i++);
        return i;
    }

}

class ThreadPoolDemo implements Runnable{

    private int i  =0 ;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": " + i++);
    }
}