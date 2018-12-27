package com.gouermazi.craw;

/**
 * @author jie·chen
 */
abstract class AbstractTargetHandler implements Runnable {
    @Override
    public void run() {
        this.resolve();
    }
    protected abstract void resolve();
}
