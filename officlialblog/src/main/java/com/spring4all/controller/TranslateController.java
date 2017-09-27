package com.spring4all.controller;

import com.lsj.trans.LANG;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;
import com.spring4all.util.MyArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by maskwang on 2017/9/14 0014.
 */
@RestController
//TODO: 将解析截止和规则放到配置文件中
public class TranslateController {

    private static final String SPRING_URL = "https://spring.io";

    private static final String SPRING_WEEK_BLOG_URL = "https://spring.io/blog";

    private static final String MATCH_RULE = "This Week in Spring";

    @RequestMapping("/translate")
    public String translate() {

        MyArrayList hrefList = new MyArrayList();

        try {

            TFactory factory = new TranslatorFactory();//完成翻译工厂类

            Document doc = Jsoup.connect(SPRING_WEEK_BLOG_URL).get();

            if (doc == null) {
                return "打开Spring周报博客失败";
            }

            String targetLink = null;

            Elements hrefs = doc.select("h2.blog--title").select("a");

            for (Element href : hrefs) {
                if (href.text().startsWith(MATCH_RULE)) {
                    targetLink = href.attr("href");
                    break;
                }
            }

            if (!StringUtils.hasText(targetLink)) {
                return "解析最新的文章标题链接地址为空，页面结构可能已经变化！";
            }

            String linkHref = SPRING_URL + targetLink;
            Document latestBlog = Jsoup.connect(linkHref).get();
            Elements allPHref = latestBlog.select("div.blog--post").select("p");
            Elements allLiHref = latestBlog.select("div.blog--post").select("ul").select("li");
            //处理p标签
            for (Element e : allPHref) {
                String traslateedSentence = factory.get("google").trans(LANG.EN, LANG.ZH, e.text());
                String traslateedContent = new StringBuilder()
                        .append(traslateedSentence)
                        .append("</br></br>")
                        .toString();

                hrefList.add(traslateedContent);
                for (Element el : e.select("a")) {
                    hrefList.add(
                            new StringBuilder()
                                    .append(el.attr("href"))
                                    .append("</br></br>")
                                    .toString()
                    );
                }
                hrefList.add("</br>");
            }
            //处理Li标签
            for (int i = 0; i < allLiHref.size(); i++) {
                hrefList.add(i + 1 + ":&nbsp&nbsp");
                //把翻译结果添加List中去
                hrefList.add(factory.get("google").trans(LANG.EN, LANG.ZH, allLiHref.get(i).text()) + "</br></br>");
                for (Element el : allLiHref.get(i).select("a")) {
                    hrefList.add(el.attr("href") + "</br></br>");
                }
            }

            return hrefList.toString();

        } catch (Exception e) {
            System.err.println("获取超时");
            e.printStackTrace();
        }
        return hrefList.toString();
    }
}

