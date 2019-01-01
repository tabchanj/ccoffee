package com.gouermazi.craw.refactoring;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chen·jie
 */
public class TeamMemberFactory implements ThreadFactory {
    private final AtomicLong at = new AtomicLong(1);
    private final String teamName;

    public TeamMemberFactory(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(teamName + "-" + at.getAndIncrement() + "-");
        return t;
    }
}
