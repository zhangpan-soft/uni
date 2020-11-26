package com.dv.uni.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/17 0017
 */
public enum ThreadPoolUtils {
    INSTANCE;
    private static final Map<String, ExecutorService> caches    = new HashMap<>();
    private static final int                          core      = 5;
    private static final int                          max       = 500;
    private static final long                         keepAlive = 0L;
    private static final TimeUnit                     unit      = TimeUnit.MICROSECONDS;

    public ExecutorService get(String key, int core, int max, long keepAlive, TimeUnit unit, BlockingQueue<Runnable> queue) {
        ExecutorService executorService = caches.get(key);
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(core, max, keepAlive, unit, queue);
        }
        return caches.get(key);
    }

    public ExecutorService get(String key, int core, int max, long keepAlive, TimeUnit unit) {
        return get(key, core, max, keepAlive, unit, new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorService get(String key,long keepAlive,TimeUnit unit){
        return get(key, core, max, keepAlive, unit, new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorService get(String key,long keepAlive,TimeUnit unit,BlockingQueue<Runnable> queue){
        return get(key, core, max, keepAlive, unit, queue);
    }

    public ExecutorService get(String key) {
        return get(key, core, max, keepAlive, unit, new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorService get(String key, int core) {
        return get(key, core, max, keepAlive, unit, new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorService get(String key, int core, int max) {
        return get(key, core, max, keepAlive, unit, new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorService get(String key, BlockingQueue<Runnable> queue) {
        return get(key, core, max, keepAlive, unit, queue);
    }

    public ExecutorService get(String key, int core, BlockingQueue<Runnable> queue) {
        return get(key, core, max, keepAlive, unit, queue);
    }

    public ExecutorService get(String key, int core, int max, BlockingQueue<Runnable> queue) {
        return get(key, core, max, keepAlive, unit, queue);
    }

}
