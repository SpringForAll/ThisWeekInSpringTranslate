package com.spring4all.factory;

import com.spring4all.enums.TranslatorNameEnum;
import com.spring4all.service.AbstractOnlineTranslator;
import com.spring4all.service.impl.BaiDuTranslator;
import com.spring4all.service.impl.GoogleTranslator;

/**
 * @author chen
 * @version V1.0
 * @date 2017/9/30
 */
public class TranslatorFactory extends AbstractTranslatorFactory {

    /**
     * google/baidu
     *
     * @param translatorName
     * @return
     */
    @Override
    public AbstractOnlineTranslator getTranslator(TranslatorNameEnum translatorName) {

        if (null == translatorName || translatorName.equals(TranslatorNameEnum.GOOGLE)) {
            return GoogleTranslator.INSTANCE;
        }
        return BaiDuTranslator.INSTANCE;
    }
}
