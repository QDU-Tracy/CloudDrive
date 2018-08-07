package org.cloudpan.entity;

public class PicPreview {
    private long previewId;
    private long fileId;
    private String url;

    public PicPreview() {
    }

    public PicPreview(long previewId, long fileId, String url) {
        this.previewId = previewId;
        this.fileId = fileId;
        this.url = url;
    }

    public long getPreviewId() {
        return previewId;
    }

    public void setPreviewId(long previewId) {
        this.previewId = previewId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PicPreview{" +
                "previewId=" + previewId +
                ", fileId=" + fileId +
                ", url='" + url + '\'' +
                '}';
    }
}
