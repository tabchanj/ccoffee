package com.gouermazi.craw.refactoring;

import java.io.File;

/**
 * @author chen·jie
 */
public interface Downloader {


    void download(String url, File saveDir);

}
