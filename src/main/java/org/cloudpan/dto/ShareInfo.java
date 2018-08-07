package org.cloudpan.dto;

import org.cloudpan.entity.Record;

/**
 * Created by Tracy on 2017/4/16.
 */
public class ShareInfo extends Record{
    private boolean success;

    private String fileMd5;

    private long fileSize;

    private String url;

    private String userName;




    public ShareInfo() {
    }

    public ShareInfo(boolean success, String fileMd5, String url) {
        this.success = success;
        this.fileMd5 = fileMd5;
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
