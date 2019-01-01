package com.gouermazi.craw.refactoring;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chen·jie
 */
public abstract class AbstractLinkDigester implements Digester {
    public Document takeDown(String seed) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        HttpGet get = new HttpGet(seed);
        get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        AtomicReference<Document> doc = new AtomicReference<>();
        try {
            httpclient.execute(get, response -> {
                InputStream source = response.getEntity().getContent();
                doc.set(Jsoup.parse(source, "UTF-8", seed));
                return null;
            });
        } finally {
            IOUtils.closeQuietly(httpclient);
        }
        return doc.get();
        /*return Jsoup.connect(seed).
                userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                        "AppleWebKit/535.2 (KHTML, like Gecko)Chrome/15.0.874.120 Safari/535.2")
                .get();*/
    }
}
