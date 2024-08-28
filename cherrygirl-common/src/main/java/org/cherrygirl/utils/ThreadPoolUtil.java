package org.cherrygirl.utils;

import java.util.concurrent.*;

/**
 * 线程池工具
 */
public class ThreadPoolUtil {
    private static volatile BlockingQueue blockingQueue;
    private static volatile ThreadPoolExecutor threadPoolExecutor;

    public static Object running(Callable callable) {
        if(blockingQueue == null){
            synchronized (ThreadPoolUtil.class){
                blockingQueue = new ArrayBlockingQueue(100);
                if(threadPoolExecutor == null){
                    threadPoolExecutor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, blockingQueue);
                }
            }
        }
        Future submit = threadPoolExecutor.submit(callable);
        try {
            return submit.get();
        } catch (InterruptedException|ExecutionException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
