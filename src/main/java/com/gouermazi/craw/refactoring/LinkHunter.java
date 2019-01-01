package com.gouermazi.craw.refactoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author chen·jie
 */
public class LinkHunter implements Agency {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkDigester.class);
    private final Executor linkTeam = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2,
            new TeamMemberFactory("linkTeam"));
    private final String saveDir;

    public LinkHunter(String saveDir) {
        this.saveDir = saveDir;
    }

    @Override
    public void takeDown(String seed) {
        try {
            TrafficLights.acquire();
            linkTeam.execute(new LinkDigester(linkTeam, seed, saveDir));
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }finally {
            TrafficLights.release();
        }


    }
}
