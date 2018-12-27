package com.gouermazi.craw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jie·chen
 */
public class Agency {
    private static final Logger LOGGER = LoggerFactory.getLogger(Agency.class);
    /**
     * deal links
     */
    private final Executor spyTeam;

    /**
     * let this dirtyWorkTeam deal the  specific link`s content(image/video...)
     */
    private final Executor dirtyWorkTeam;

    private final String saveDir;

    private static final ThreadFactory spyFactory = new SpyFactory("spyAgency");


    public Agency(Executor executor, Executor dirtyWorkTeam,String saveDir) {
        this.spyTeam = executor;
        this.dirtyWorkTeam = dirtyWorkTeam;
        this.saveDir = saveDir;
    }

    public Agency(String saveDir) {
        this.spyTeam = Executors.newCachedThreadPool(spyFactory);
        this.dirtyWorkTeam = Executors.newCachedThreadPool(spyFactory);
        this.saveDir = saveDir;
    }

    public void execute(List<String> initUrls) {
        if (initUrls != null) {
            for (String url : initUrls) {
                if (url != null && url.startsWith("http")) {
                    spyTeam.execute(new ImageTargetHandler(url, dirtyWorkTeam, spyTeam, new File(saveDir)));
                }
            }
        }
    }

    /**
     * thread factory
     */
    static class SpyFactory implements ThreadFactory {
        /**
         * thread count
         */
        private final AtomicInteger spyCount = new AtomicInteger(0);

        /**
         * pool name prefix
         */
        private final String teamName;

        SpyFactory(String teamName) {
            this.teamName = teamName;
        }

        @Override
        public Thread newThread(Runnable r) {
            int curNo = spyCount.incrementAndGet();
            return new Thread(r, teamName + "--" + curNo + "-thread- ");
        }
    }
}
