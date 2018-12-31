package com.gouermazi.craw.refactoring;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collector;

/**
 * @author chen·jie
 */
public class HuntDog extends AbstractAgency {
    private static final Logger LOGGER = LoggerFactory.getLogger(HuntDog.class);
    private final Executor linkExecutor = Executors.newCachedThreadPool();
    private final Executor picExecutor = Executors.newCachedThreadPool();
    private final Executor fatFileExecutor = Executors.newCachedThreadPool();
    private final String saveDir;

    public HuntDog(String saveDir) {
        this.saveDir = saveDir;
    }

    @Override
    public void resolve(Document document) {
            //解析出来普通href, 图片链接， 视频链接
        try {
            List<String> pics =  getPicLink(document);
            List<String> refs =  getHref(document);
            List<String> vs =  getVideoLink(document);
            pics.stream().forEach(pic -> {
                try {
                    TrafficLights.PRODUCE_CONTROL.acquire();
                    picExecutor.execute(new ImageDigester(pic, new File(saveDir)));
                } catch (InterruptedException e) {
                    LOGGER.error("" , e);
                }finally {
                    TrafficLights.PRODUCE_CONTROL.release();
                }
            });
            refs.stream().forEach(href -> {
                try {
                    TrafficLights.PRODUCE_CONTROL.acquire();
                    takeDown(href);
                } catch (InterruptedException e) {
                    LOGGER.error("" , e);
                } catch (IOException e) {
                    LOGGER.error("" , e);
                } finally {
                    TrafficLights.PRODUCE_CONTROL.release();
                }
            });
            vs.stream().forEach(src -> {
                try {
                    TrafficLights.PRODUCE_CONTROL.acquire();
                    fatFileExecutor.execute(new VideoDigester(src, new File(saveDir + "/1video")));
                } catch (InterruptedException e) {
                    LOGGER.error("" , e);
                } finally {
                    TrafficLights.PRODUCE_CONTROL.release();
                }
            });
        } catch (MalformedURLException e) {
            LOGGER.error("" , e);
        }

    }
    protected List<String> getPicLink(Document document) throws MalformedURLException {
        String url = document.location();
        URL origin = new URL(url);
        String protco = origin.getProtocol();
        String host = origin.getHost();
        Elements imgs = document.body().getElementsByTag("img");
        List<String> res = imgs.stream().collect(Collector.of(
                LinkedList::new,
                (list, ele) -> {
                    String imgSrc = ele.attr("src");
                    if (imgSrc != null) {
                        if (imgSrc.startsWith("http")) {
                        } else if (imgSrc.startsWith("//")) {
                            imgSrc = protco + "://" + imgSrc.substring(2);
                        } else if(imgSrc.startsWith("data:")){
                        }
                        else {
                            imgSrc = protco + "://" + host + imgSrc;
                        }
                        LOGGER.info("pic link =  " + imgSrc);
                    }
                    list.add(imgSrc);
                },
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        ));
        return res;
    }

    protected List<String> getHref(Document document) throws MalformedURLException {
        String url = document.location();
        URL origin = new URL(url);
        String protco = origin.getProtocol();
        String host = origin.getHost();
        Elements aTags = document.body().getElementsByTag("a");
        List<String> res = aTags.stream().collect(Collector.of(
                LinkedList::new,
                (list, ele) -> {
                    String href = ele.attr("href");
                    if (href != null) {
                        if (href.startsWith("http")) {
                        } else if (href.startsWith("//")) {
                            href = protco + "://" + href.substring(2);
                        } else {
                            href = protco + "://" + host + href;
                        }
                        LOGGER.info("href link =  " + href);
                    }
                    list.add(href);
                },
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        ));
        return res;
    }

    protected List<String> getVideoLink(Document document) throws MalformedURLException {
        String url = document.location();
        URL origin = new URL(url);
        String protco = origin.getProtocol();
        String host = origin.getHost();
        Elements videos = document.body().select("div video");
        Elements vidInfo = document.body().select(".vidsnfo");
        List<String> res = videos.stream().collect(Collector.of(
                LinkedList::new,
                (list, ele) -> {
                    String vSrc = ele.attr("src");
                    if (vSrc != null) {
                        if (vSrc.startsWith("http")) {
                        } else if (vSrc.startsWith("//")) {
                            vSrc = protco + "://" + vSrc.substring(2);
                        } else {
                            vSrc = protco + "://" + host + vSrc;
                        }
                        LOGGER.info("video link =  " + vSrc);
                    }
                    list.add(vSrc);
                },
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        ));
        res.addAll(vidInfo.stream().collect(Collector.of(ArrayList::new,
                (list, ele) -> {
                    String vSrc = ele.attr("data-vnfo");
                    LOGGER.info("======================================================");
                    LOGGER.info(vSrc);
                    vSrc = vSrc.substring(vSrc.indexOf("\\") + 1)
                            .replaceAll("\\\\", "")
                            .replace("cdn", "cdn3");
                    vSrc = protco + "://" + host + vSrc;
                    vSrc = vSrc.substring(0,vSrc.length()-2);
                    LOGGER.info(vSrc);
                    LOGGER.info("======================================================");
                    list.add(vSrc);
                },
                (left, right) -> {
                    left.addAll(right);
                    return left;
                })));
        return res;
    }
}
