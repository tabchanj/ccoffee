package com.gouermazi.craw.refactoring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author chen·j
 */
public abstract class AbstractAgency implements Agency {
    @Override
    public void takeDown(String seed) throws IOException {
        Document doc = Jsoup.connect(seed).
                userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                        "AppleWebKit/535.2 (KHTML, like Gecko)Chrome/15.0.874.120 Safari/535.2")
                .get();
        resolve(doc);
    }

    public abstract void resolve(Document document);
}
