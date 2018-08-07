package org.cloudpan.entity;

import java.util.Date;

/**
 * Created by TracyM on 2017/3/22.
 */
public class Record {
    private long recordId;
    private long userId;
    private long fileId;
    private String customName;
    private short share;
    private Date createTime;
    private String recordMd5;
    private short fileType;
    private String filePath;

    public Record() {
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public short getShare() {
        return share;
    }

    public void setShare(short share) {
        this.share = share;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRecordMd5() {
        return recordMd5;
    }

    public void setRecordMd5(String recordMd5) {
        this.recordMd5 = recordMd5;
    }

    public short getFileType() {
        return fileType;
    }

    public void setFileType(short fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public String toString() {
        return "Record{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", fileId=" + fileId +
                ", customName='" + customName + '\'' +
                ", share=" + share +
                ", createTime=" + createTime +
                ", recordMd5='" + recordMd5 + '\'' +
                ", fileType=" + fileType +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
