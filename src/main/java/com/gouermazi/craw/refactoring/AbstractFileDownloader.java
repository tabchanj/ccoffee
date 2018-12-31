package com.gouermazi.craw.refactoring;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author chen·jie
 */
public abstract class AbstractFileDownloader implements Downloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileDownloader.class);

    @Override
    public void download(String url, File saveDir) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (saveDir != null && url != null && url.startsWith("http")) {
            try {
                CloseableHttpClient httpclient = HttpClients.custom()
                        .setRedirectStrategy(new LaxRedirectStrategy())
                        .build();
                try {
                    HttpGet get = new HttpGet(url);
                    httpclient.execute(get, response -> {
                        InputStream source = response.getEntity().getContent();
                        long length = response.getEntity().getContentLength();
                        int ext = url.lastIndexOf(".");
                        String extStr = url.substring(ext);
                        if (fileBytesLimitLeft() <= 0 ? true : length >= fileBytesLimitLeft()
                                && fileBytesLimitRight() <= 0 ? true : length <= fileBytesLimitRight()) {
                            LOGGER.info("file size = " + source.available());
                            LOGGER.info("downloading ..." + url);
                            File dest = new File(saveDir.getPath() + "/" + UUID.randomUUID().toString() + extStr);
                            if (!dest.exists())
                                dest.getParentFile().mkdirs();
                            FileUtils.copyInputStreamToFile(source, dest);
                        }
                        return null;
                    });
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                } finally {
                    IOUtils.closeQuietly(httpclient);
                }
            } catch (Exception e) {
                LOGGER.error("download failed ...", e);
            }
        }
    }

    public abstract int fileBytesLimitLeft();

    public abstract int fileBytesLimitRight();

}
