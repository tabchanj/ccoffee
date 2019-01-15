package com.gouermazi.craw.refactoring;

import com.gargoylesoftware.htmlunit.httpclient.HtmlUnitRedirectStrategie;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
        AtomicReference<Document> doc = new AtomicReference();
        try (
                CloseableHttpClient httpclient = HttpClients.custom()
                        .setRedirectStrategy(new HtmlUnitRedirectStrategie())
                        .build()
        ) {
            HttpGet get = new HttpGet(seed);
            get.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            httpclient.execute(get, response -> {
                InputStream source = response.getEntity().getContent();
                doc.set(Jsoup.parse(source, "UTF-8", seed));
                return null;
            });
        }
        return doc.get();
    }
}
