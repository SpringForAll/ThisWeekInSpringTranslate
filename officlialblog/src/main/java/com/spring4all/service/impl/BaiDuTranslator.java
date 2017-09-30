package com.spring4all.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring4all.enums.LanguageEnum;
import com.spring4all.service.AbstractOnlineTranslator;
import com.spring4all.service.translate.http.HttpParams;
import com.spring4all.service.translate.http.HttpPostParams;

/**
 * @author chen
 * @version V1.0
 * @date 2017/9/30
 */
public class BaiDuTranslator extends AbstractOnlineTranslator {

    public static final BaiDuTranslator INSTANCE = new BaiDuTranslator();

    private BaiDuTranslator() {
        langMap.put(LanguageEnum.EN, "en");
        langMap.put(LanguageEnum.ZH, "zh");
    }

    @Override
    public String getResponse(LanguageEnum from, LanguageEnum targ, String query) throws Exception {

        HttpParams params = new HttpPostParams()
                .put("from", langMap.get(from))
                .put("to", langMap.get(targ))
                .put("query", query)
                .put("transtype", "translang")
                .put("simple_means_flag", "3");

        return params.send2String("http://fanyi.baidu.com/v2transapi");
    }

    @Override
    protected String parseString(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray segments = jsonObject.getJSONObject("trans_result").getJSONArray("data");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < segments.size(); i++) {
            result.append(i == 0 ? "" : "\n");
            result.append(segments.getJSONObject(i).getString("dst"));
        }
        return new String(result);
    }
}

