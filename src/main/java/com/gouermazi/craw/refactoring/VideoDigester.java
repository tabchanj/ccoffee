package com.gouermazi.craw.refactoring;

import java.io.File;

/**
 * @author chen·jie
 */
public class VideoDigester implements Digester {
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
        try {
            TrafficLights.PRODUCE_CONTROL.acquire();
            downloader.download(url, saveDir);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            TrafficLights.PRODUCE_CONTROL.release();
        }
    }

    @Override
    public void run() {
        digest();
    }
}
