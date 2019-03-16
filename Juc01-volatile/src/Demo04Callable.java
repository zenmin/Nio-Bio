import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/16 20:39
 *
 * 创建线程的方式3：实现callabel接口 相比Runnable接口  他可以有返回值  并且可以抛出异常
 *  使用callable方式  需要FutureTask支持  用于接收运算结果
 *
 */
public class Demo04Callable {

    public static void main(String args[]){
        CallableDemo callableDemo = new CallableDemo();

        // 需要使用FutureTask包装  用于接收返回结果
        FutureTask<Long> future = new FutureTask<>(callableDemo);

        //  开启线程
        new Thread(future).start();

        try {
            Long integer = future.get();    // 接收到结果 才会调下面方法  可以用于闭锁
            System.out.println("==========");
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
class CallableDemo implements Callable{

    @Override
    public Long call() throws Exception {

        long j = 0 ;

        for (long i = 0;i<999999999999999L;i++){
            j = i;
        }
        return j;
    }
}