package org.cloudpan.entity;

import java.util.Date;

/**
 * Created by TracyM on 2017/3/22.
 */
public class File {
    private long fileId;
    private String filePath;
    private String fileMd5;
    private long fileSize;

    public File() {
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", filePath='" + filePath + '\'' +
                ", fileMd5='" + fileMd5 + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
