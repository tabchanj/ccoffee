package com.gouermazi.craw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jie·chen
 */
public class ImageTargetHandler extends AbstractTargetHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageTargetHandler.class);
    private final String target;
    private final Executor dirtyWorkTeam;
    private final Executor spyTeam;
    private final File saveDir;

    public ImageTargetHandler(String target, Executor dirtyWorkTeam, Executor spyTeam, File saveDir) {
        this.target = target;
        this.dirtyWorkTeam = dirtyWorkTeam;
        this.spyTeam = spyTeam;
        this.saveDir = saveDir;
    }

    @Override
    protected void resolve() {
        try {
            URL url = new URL(target);
            String protco = url.getProtocol();
            String host = url.getHost();
            Document doc = Jsoup.connect(target).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 " +
                    "(KHTML, like Gecko)Chrome/15.0.874.120 Safari/535.2")
                    .get();
            Elements elements = doc.body().getElementsByTag("img");
            List<Runnable> targets = new LinkedList<>();
            List<Callable<Void>> dirtyWorks = new LinkedList<>();
            for (Element element : elements) {
                String imgSrc = element.attr("src");
                if (imgSrc != null) {
                    if (imgSrc.startsWith("http")) {
                    } else if (imgSrc.startsWith("//")) {
                        imgSrc = protco + "://" + imgSrc.substring(2);
                    } else {
                        imgSrc = protco + "://" + host + imgSrc;
                    }
                    LOGGER.info("pic link =  " + imgSrc);
                }
                dirtyWorks.add(new ImageDirtyWorkHandler(imgSrc, saveDir));
            }
            LOGGER.info("====================================");
            LOGGER.info("====total pics count = " + dirtyWorks.size() + "=====");
            LOGGER.info("====================================");
            ((ThreadPoolExecutor)dirtyWorkTeam).invokeAll(dirtyWorks, 15, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("resolve link error", e);
        }
    }
}
