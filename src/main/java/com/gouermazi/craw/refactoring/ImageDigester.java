package com.gouermazi.craw.refactoring;

import java.io.File;

/**
 * @author chen·jie
 */
public class ImageDigester implements Digester {
    private final String url;
    private final File saveDir;
    private final Downloader downloader;

    public ImageDigester(String url, File saveDir) {
        this.url = url;
        this.saveDir = saveDir;
        this.downloader = new ImageDownloader();
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
