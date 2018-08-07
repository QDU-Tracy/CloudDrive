package org.cloudpan.Cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.cloudpan.util.CosUtil;
import org.junit.Test;

import java.io.File;


public class CosTest {
    @Test
    public void UploadTest(){
        COSClient cosClient = CosUtil.getCred();

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = new File("D:\\申请表.pdf");
        // 指定要上传到 COS 上的路径
        String key = "/pdf/upload_single_demo.pdf";
        String bucketName = CosUtil.getBucketName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();

    }

    @Test
    public void DownloadTest(){
        COSClient cosClient = CosUtil.getCred();

        // 指定要下载到的本地路径
        File downFile = new File("D:\\CosDownload\\test.pdf");
        // 指定要下载的文件所在的 bucket 和路径
        String bucketName = CosUtil.getBucketName();
        String key = "/pdf/upload_single_demo.pdf";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);

        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

}
