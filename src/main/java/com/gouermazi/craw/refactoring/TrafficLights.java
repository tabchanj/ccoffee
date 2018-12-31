package com.gouermazi.craw.refactoring;

import java.util.concurrent.Semaphore;

/**
 * @author chen·jie
 */
public class TrafficLights {
    public static final Semaphore PRODUCE_CONTROL = new Semaphore(Runtime.getRuntime().availableProcessors() + 1);
}
