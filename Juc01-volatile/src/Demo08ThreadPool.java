import java.util.concurrent.*;

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

        // 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


        // 给线程池分配任务  普通Runnable
        for (int i = 0;i < 10 ;i++){
            executorService.submit(t);
        }

        // 给线程池分配任务  Callable方式
        for (int i = 0;i < 10 ;i++){
            Future<Integer> future = scheduledExecutorService.schedule(new Callable<Integer>() {
                int i = 1;
                @Override
                public Integer call() throws Exception {
                    return i + (int)(Math.random() * 100);
                }
            },3,TimeUnit.SECONDS);  // 延时调用
            System.out.println(Thread.currentThread().getName() + ":" + future.get());
        }


        // 关闭线程池
        executorService.shutdown();
        scheduledExecutorService.shutdownNow();
    }



}

class ThreadPoolDemo implements Runnable{

    private int i  =0 ;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": " + i++);
    }
}