/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/10 21:34
 * <p>
 * volatile  当多个线程访问共享数据时  保证内存中的数据可见
 * 相对于synchronized是轻量级的同步策略
 * <p>
 * 注意
 * volatile不具备互斥性
 * volatile不能保证数据的原子性
 */
public class Demo01 {

    public static void main(String args[]) {
        Stack stack = new Stack();
        new Thread(stack).start();


        // synchronized 处理变量共享 效率低
//            while (true){
//                synchronized (stack){
//                if(stack.isA()){
//                    System.out.println("+++Main+++");
//                }
//            }
//        }


        while (true) {
            if (stack.isA()) {
                System.out.println("+++Main+++");
            }
        }

    }


}

class Stack implements Runnable {

    private volatile boolean isA = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
            isA = true;
            System.out.println("Stack Thread:" + isA);
        } catch (InterruptedException e) {
        }
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }
}

