package javaLearning.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zuston on 16-11-15.
 */
public class zThread extends Thread{
    public static void main(String[] args) {
        Thread t1 = new zThread();
        Thread t2 = new zThread();
        t2.start();
        t1.start();

        ExecutorService exe = Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            exe.execute(new zThread());
        }
        exe.shutdown();
    }


    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName());
            Thread.yield();
        }
    }
}
