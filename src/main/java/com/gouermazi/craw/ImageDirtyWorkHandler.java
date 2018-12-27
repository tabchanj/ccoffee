package com.gouermazi.craw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @author jie·chen 2018-12-27
 */
public class ImageDirtyWorkHandler extends AbstractDirtyWorkHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDirtyWorkHandler.class);
    private final String imageUrl;
    private final File saveDir;

    public ImageDirtyWorkHandler(String imageUrl, File saveDir) {
        this.imageUrl = imageUrl;
        this.saveDir = saveDir;
    }

    @Override
    protected Void reslove() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("interrupted....", e);
        }
        if (saveDir != null && imageUrl != null && imageUrl.startsWith("http")) {
            URL url;
            try {
                url = new URL(imageUrl);
                URLConnection uri = url.openConnection();
                InputStream is = uri.getInputStream();
                File dest = new File(saveDir.getPath() + "/" + UUID.randomUUID().toString());
                if (!dest.exists())
                    dest.getParentFile().mkdirs();
                OutputStream os = new FileOutputStream(dest);

                byte[] buf = new byte[1024];
                int len = -1;

                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
            } catch (Exception e) {
                LOGGER.error("download failed ...", e);
            }
        }
        return null;
    }
}
