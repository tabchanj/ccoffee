package com.gouermazi.craw.refactoring;

import java.io.File;

/**
 * @author chen·jie
 */
public class VideoDownloader extends AbstractFileDownloader {

    @Override
    public void download(String url, File saveDir) {
        super.download(url, saveDir);
    }

    @Override
    public int fileBytesLimitLeft() {
        return 1024 * 200 * 1024;
    }

    @Override
    public int fileBytesLimitRight() {
        return 0;
    }
}
