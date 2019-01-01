package com.gouermazi.craw.refactoring;

import java.io.File;

/**
 * @author chen·jie
 */
public class ImageDownloader extends AbstractFileDownloader {

    @Override
    public void download(String url, File saveDir) {
        super.download(url, saveDir );
    }
    @Override
    public int fileBytesLimitLeft() {
        return 50 * 1024;
    }

    @Override
    public int fileBytesLimitRight() {
        return 0;
    }
}
