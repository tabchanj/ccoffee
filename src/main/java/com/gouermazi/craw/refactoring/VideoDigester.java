package com.gouermazi.craw.refactoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author chen·jie
 */
public class VideoDigester implements Digester {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoDigester.class);
    private final String url;
    private final File saveDir;
    private final Downloader downloader;

    public VideoDigester(String url, File saveDir) {
        this.url = url;
        this.saveDir = saveDir;
        this.downloader =  new VideoDownloader();
    }

    @Override
    public void digest() {
        downloader.download(url, saveDir);
    }

    @Override
    public void run() {
        digest();
    }
}
