package com.spring4all.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;

/**
 * Created by maskwang on 2017/10/9 0009.
 */
public class QrCodeUtil {
    public static byte[] createCode(String url){
        ByteArrayOutputStream bout =
                QRCode.from(url)
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();
        return bout.toByteArray();
    }
}
