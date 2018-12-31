package com.gouermazi.craw.refactoring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author chen·j
 */
public abstract class AbstractAgency implements Agency {
    @Override
    public void takeDown(String seed) throws IOException {
        Document doc = Jsoup.connect(seed).
                userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                        "AppleWebKit/535.2 (KHTML, like Gecko)Chrome/15.0.874.120 Safari/535.2")
                .get();
        resolve(doc);
        /**HtmlUnit请求web页面*/
        /*WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page = wc.getPage(seed);
        String pageXml = page.asXml(); //以xml的形式获取响应文本

        *//**jsoup解析文档*//*
        Document doc = Jsoup.parse(pageXml, seed);


        resolve(doc);*/
    }

    public abstract void resolve(Document document);
}
