package org.cloudpan.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

public class CosUtil {
    private static COSCredentials cred;
    private static COSClient cosclient;
    private static String bucketName = "test-1252882699";

    public  static COSClient getCred(){
        // 1 初始化用户身份信息(secretId, secretKey)
        cred = new BasicCOSCredentials("AKID41nNq7w5mKiz4yoOFyVqz99zd7we7sET", "EIAqFYptqeGgtv46xmU0KTP9h7LNRMfq");
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = "test-1252882699";

        return cosclient;
    }

    public static String getBucketName(){
        return bucketName;
    }
}
