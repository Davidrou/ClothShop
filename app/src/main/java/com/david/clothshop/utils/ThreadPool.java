package com.david.clothshop.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luxiaolin on 18/3/11.
 */

public class ThreadPool {
    private static ExecutorService sExecutorService;
    private static int THREAD_POOL_COUNT = 10;

    static {
        sExecutorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
    }

    public static void execute(Runnable runnable){
        sExecutorService.execute(runnable);
    }
}
