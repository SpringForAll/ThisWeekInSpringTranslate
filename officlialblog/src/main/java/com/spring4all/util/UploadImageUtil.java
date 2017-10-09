package com.spring4all.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maskwang on 2017/10/9 0009.
 */
public class UploadImageUtil {

    public static Logger logger= LoggerFactory.getLogger(UploadImageUtil.class);

    public static final String RETURNURL="http://oxjys514c.bkt.clouddn.com/";
    public static final String ACCESS_KEY = "C35NvihMxcnt4iVZU3GVepxVYnxz06XQv4m4hWkN"; // 你的access_key
    public static final String SECRET_KEY = "qMrvAcHaDx9OiMbBHSEXc-UxFxtgznBp5B1IG1mo"; // 你的secret_key
    public static final String BUCKET_NAME = "spring4all"; // 你的buket_name

    public static String upLoad(byte[]bytes,String imageName){
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        UploadManager uploadManager = new UploadManager();
        try {
            //调用put方法上传
            Response res = uploadManager.put(bytes,imageName,auth.uploadToken(BUCKET_NAME));
            //bytes, RETURNNAME, getUpToken(auth,BUCKET_NAME)
            //打印返回的信息
            logger.info(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.info(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return RETURNURL+imageName;
    }
    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public  static String getUpToken(Auth auth,String bucketName){
        return auth.uploadToken(bucketName);
    }

}
