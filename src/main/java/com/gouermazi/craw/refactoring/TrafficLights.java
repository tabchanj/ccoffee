package com.gouermazi.craw.refactoring;

import java.util.concurrent.Semaphore;

/**
 * @author chen·jie
 */
public class TrafficLights {
    public static final Semaphore PRODUCE_CONTROL = new Semaphore(Runtime.getRuntime().availableProcessors() + 1);

    public static void acquire() throws InterruptedException {
        PRODUCE_CONTROL.acquire();
    }

    public static void release() {
        PRODUCE_CONTROL.release();
    }
}
