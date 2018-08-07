package org.cloudpan.service;

import org.cloudpan.dto.UploadInfo;
import org.cloudpan.entity.File;

import java.util.Date;

/**
 * Created by TracyM on 2017/3/23.
 */
public interface FileService {

    int addFile(String fileMd5,long userId,String fileName,String customName,short share,Date createTime,long fileSize);

    int addRecord(long userId,long fileId,String customName,short share,Date createTime,String recordMd5,short fileType);

    String getFilePath(long fileId);

    String getFileMd5(long fileId);

    long getFileSize(long fileId);

    File getFileByMd5(String fileMd5);

    File getFileById(long fileId);

}
