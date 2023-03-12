package com.jny.android.demo.okhttp;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;

/**
 * 任务分发器，将任务分发到对应的线程执行
 */
public class Dispatcher {
    /**
     * 最大连接数，超过放到ready队列
     */
    private int maxRequest = 64;
    /**
     * 每台主机最大连接数，超过放到ready队列
     */
    private int maxRequestsPerHost = 5;

    @Nullable
    private ExecutorService executorService;

    /**
     * 异步准备中队列
     */
    private final Deque<RealCall> readyAsyncCalls = new ArrayDeque<>();
    /**
     * 异步运行中队列，满了先放到准备中队列
     */
    private final Deque<RealCall> runningAsyncCalls = new ArrayDeque<>();
    /**
     * 同步运行中队列
     */
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();

    synchronized void execute(RealCall call) {
        runningSyncCalls.add(call);
    }
}
