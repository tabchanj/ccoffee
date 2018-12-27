package com.gouermazi.craw;

import java.util.concurrent.Callable;

/**
 * @author jie·chen
 */
public abstract class AbstractDirtyWorkHandler implements Callable<Void> {

    protected abstract Void reslove();

    @Override
    public Void call() throws Exception {
        return reslove();
    }
}
