package com.testapptwo.features;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created on 19.01.2017.
 */

public class AppExecutors {
    public static final ExecutorService DB_EXECUTOR = Executors.newSingleThreadExecutor();
    public static final ExecutorService NETWORK_EXECUTOR = Executors.newFixedThreadPool(2);

    public static final Scheduler DB_SCHEDULER = Schedulers.from(DB_EXECUTOR);
    public static final Scheduler NETWORK_SCHEDULER = Schedulers.from(NETWORK_EXECUTOR);
}
