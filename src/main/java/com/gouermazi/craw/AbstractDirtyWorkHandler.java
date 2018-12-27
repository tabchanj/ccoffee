package com.gouermazi.craw;

import java.util.concurrent.Callable;

/**
 * @author jie·chen 2018-12-27
 */
public abstract class AbstractDirtyWorkHandler implements Callable<Void> {

    protected abstract Void reslove();

    @Override
    public Void call() throws Exception {
        return reslove();
    }
}
