import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/11 21:42
 *
 * 原子变量：juc 包下提供了常用的原子变量
 *         1、volatile 保证内存可见性
 *         2、CAS算法 保证数据原子性
 *         CAS包含是三个操作数
 *          内存值   预估值  更新值
 *          当内存值=预估值时  把更新值复制给内存值  否则不做操作
 *
 *
 */
public class Demo02Atomic {

    public static void main(String args[]){
        AtomicNum atomicNum = new AtomicNum();

        for (int i = 0; i < 10; i++) {
            new Thread(atomicNum).start();
        }
    }

}


class AtomicNum implements Runnable{

    private AtomicInteger num = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        System.out.println(numPlus());

    }

    private Integer numPlus(){
        return num.getAndIncrement();
    }

}