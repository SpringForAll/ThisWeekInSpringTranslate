package com.spring4all.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.spring4all.enums.LanguageEnum;
import com.spring4all.service.AbstractOnlineTranslator;
import com.spring4all.service.translate.http.HttpParams;
import com.spring4all.service.translate.http.HttpPostParams;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author chen
 * @version V1.0
 * @date 2017/9/30
 */
public final class GoogleTranslator extends AbstractOnlineTranslator {

    public static final GoogleTranslator INSTANCE = new GoogleTranslator();

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    private GoogleTranslator() {
        langMap.put(LanguageEnum.EN, "en");
        langMap.put(LanguageEnum.ZH, "zh-CN");
        langMap.put(LanguageEnum.RU, "ru");
    }

    @Override
    protected String getResponse(LanguageEnum from, LanguageEnum targ, String query) throws Exception {

        HttpParams params = null;
        params = new HttpPostParams();        //统一采用post，若字符长度小于999用get也可以的
        String tk = tk(query);

        params.put("client", "t")
                .put("sl", langMap.get(from))
                .put("tl", langMap.get(targ))
                .put("hl", "zh-CN")
                .put("dt", "at")
                .put("dt", "bd")
                .put("dt", "ex")
                .put("dt", "ld")
                .put("dt", "md")
                .put("dt", "qca")
                .put("dt", "rw")
                .put("dt", "rm")
                .put("dt", "ss")
                .put("dt", "t")

                .put("ie", "UTF-8")
                .put("oe", "UTF-8")
                .put("source", "btn")
                .put("srcrom", "1")
                .put("ssel", "0")
                .put("tsel", "0")
                .put("kc", "11")
                .put("tk", tk)
                .put("q", query);

        return params.send2String("http://translate.google.cn/translate_a/single");
    }

    @Override
    protected String parseString(String jsonString) {
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        JSONArray segments = jsonArray.getJSONArray(0);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < segments.size(); i++) {
            result.append(segments.getJSONArray(i).getString(0));
        }

        return new String(result);
    }

    private String tk(String val) throws Exception {
        String script = "function tk(a) {"
                + "var TKK = ((function() {var a = 561666268;var b = 1526272306;return 406398 + '.' + (a + b); })());\n"
                + "function b(a, b) { for (var d = 0; d < b.length - 2; d += 3) { var c = b.charAt(d + 2), c = 'a' <= c ? c.charCodeAt(0) - 87 : Number(c), c = '+' == b.charAt(d + 1) ? a >>> c : a << c; a = '+' == b.charAt(d) ? a + c & 4294967295 : a ^ c } return a }\n"
                + "for (var e = TKK.split('.'), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {"
                + "var c = a.charCodeAt(f);"
                + "128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)"
                + "}"
                + "a = h;"
                + "for (d = 0; d < g.length; d++) a += g[d], a = b(a, '+-a^+6');"
                + "a = b(a, '+-3^+b+-f');"
                + "a ^= Number(e[1]) || 0;"
                + "0 > a && (a = (a & 2147483647) + 2147483648);"
                + "a %= 1E6;"
                + "return a.toString() + '.' + (a ^ h)\n"
                + "}";

        engine.eval(script);
        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("tk", val);
    }
}