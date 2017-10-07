package com.spring4all.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsoupUtil {
    private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

    public static Document getDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            logger.error("occur error:{}", e);
        }
        return document;

    }
}
