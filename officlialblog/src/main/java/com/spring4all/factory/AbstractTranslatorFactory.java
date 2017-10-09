package com.spring4all.factory;

import com.spring4all.enums.TranslatorNameEnum;
import com.spring4all.service.AbstractOnlineTranslator;

public abstract class AbstractTranslatorFactory {
    /**
     * google/baidu
     *
     * @param translatorName
     * @return
     */
    public abstract AbstractOnlineTranslator getTranslator(TranslatorNameEnum translatorName);
}
