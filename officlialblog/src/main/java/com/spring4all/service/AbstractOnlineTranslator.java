package com.spring4all.service;


import com.spring4all.enums.LanguageEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @version V1.0
 * @date 2017/9/30
 */
public abstract class AbstractOnlineTranslator{
    protected Map<LanguageEnum, String> langMap = new HashMap<>();					//语言映射，由子类完成

    final public String trans(LanguageEnum from, LanguageEnum target, String query) throws Exception {
        String response = "";
        try{
            response = getResponse(from, target, query);
            String result = parseString(response);
            return result;
        }catch(Exception e){
            return response;
        }
    }

    abstract protected String getResponse(LanguageEnum from, LanguageEnum target, String query) throws Exception ;
    abstract protected String parseString(String jsonString);
}
