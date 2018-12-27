package com.gouermazi.craw;

/**
 * @author jie·chen 2018-12-27
 */
abstract class AbstractTargetHandler implements Runnable {
    @Override
    public void run() {
        this.resolve();
    }
    protected abstract void resolve();
}
