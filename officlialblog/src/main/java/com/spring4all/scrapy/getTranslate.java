package com.spring4all.scrapy;

import com.lsj.trans.LANG;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maskwang on 2017/9/14 0014.
 */
@RestController
public class GetTranslate {
    @RequestMapping("/getTranslate")
    public String getTranslate() {
        MyArrayList hrefList = new MyArrayList();
        try {
            TFactory factory = new TranslatorFactory();//完成翻译工厂类
            Document doc = Jsoup.connect("https://spring.io/blog").get();
            Elements links = doc.select("h2.blog--title").select("a");
            Pattern pattern = Pattern.compile("^This Week in Spring.*");//匹配最新一期的
            Element targetLink = null;
            for (Element e : links) {
                Matcher matcher = pattern.matcher(e.text());
                if (matcher.matches()) {
                    targetLink = e;
                    break;
                }
            }
            targetLink = targetLink.select("a").first();//进入最新一期的文章
            String linkHref = "https://spring.io" + targetLink.attr("href");
            Document doc1 = Jsoup.connect(linkHref).get();
            Elements allPHref = doc1.select("div.blog--post").select("p");
            Elements allLiHref = doc1.select("div.blog--post").select("ul").select("li");
            //处理p标签
            for (Element e : allPHref) {
                //把翻译结果添加List中去
                hrefList.add(factory.get("google").trans(LANG.EN, LANG.ZH, e.text()) + "</br></br>");
                for (Element el : e.select("a")) {
                    hrefList.add(el.attr("href") + "</br></br>");
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
            System.out.println("获取超时");
            e.printStackTrace();
        }
        return hrefList.toString();
    }
}

//重写toStirng方法，不要默认的
class MyArrayList extends ArrayList<String> {
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder buffer = new StringBuilder(size() * 16);
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != this) {
                buffer.append(next);
            } else {
                buffer.append("(this Collection)");
            }
            if (it.hasNext()) {

            }
        }
        return buffer.toString();
    }
}