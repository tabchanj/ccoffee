package com.gouermazi.craw.refactoring;

import java.io.IOException;
import java.util.List;

/**
 * @author chen·jie
 */
public interface Agency {
    /**
     * add seeds
     * @param seed
     */
    void takeDown(String seed) throws IOException;
}
