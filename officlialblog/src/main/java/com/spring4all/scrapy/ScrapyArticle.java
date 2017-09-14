package com.spring4all.scrapy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maskwang on 2017/9/14 0014.
 */
@RestController
public class ScrapyArticle {
    @RequestMapping("/getLinks")
    public String getLinks() {
        MyArrayList hrefList = new MyArrayList();
        try {
            Document doc = Jsoup.connect("https://spring.io/blog").get();
            Elements links = doc.select("h2.blog--title").select("a");
            Pattern pattern = Pattern.compile("^This Week.*");
            Element targetLink = null;
            for (Element e : links) {
                System.out.println(e.text());
                Matcher matcher = pattern.matcher(e.text());
                if (matcher.matches()) {
                    targetLink = e;
                    break;
                }
            }
            targetLink = targetLink.select("a").first();
            String linkHref = "https://spring.io" + targetLink.attr("href");
            Document doc1 = Jsoup.connect(linkHref).get();
            Elements allLiHref = doc1.select("div.blog--post").select("ul").select("li");
            Elements allPHref = doc1.select("div.blog--post").select("p");
            for (Element e : allPHref) {
                for(Element el:e.select("a")){
                    hrefList.add(el.attr("href")+"</br>");
                }
                hrefList.add("</br>");
            }
            int i=1;
            for(Element e:allLiHref){
                hrefList.add(i+":&nbsp&nbsp");
                for(Element el:e.select("a")){
                    hrefList.add(el.attr("href")+"</br>");
                }
                hrefList.add("</br></br>");
                i++;
            }
        } catch (IOException e) {
            System.out.println("获取超时");
            e.printStackTrace();
        }
        return hrefList.toString();
    }
}
class MyArrayList extends ArrayList<String>{
    @Override
    public String toString(){
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
