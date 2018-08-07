package org.cloudpan.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by TracyM on 2017/3/23.
 */
public class UploadInfo {
    private MultipartFile multipartFile;
    private long fileMd5;
    private short share;
    private String customName;
    private Date createTime;

    public UploadInfo() {
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public long getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(long fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public short getShare() {
        return share;
    }

    public void setShare(short share) {
        this.share = share;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "fileMd5=" + fileMd5 +
                ", share=" + share +
                ", customName='" + customName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
