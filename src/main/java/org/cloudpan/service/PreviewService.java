package org.cloudpan.service;

public interface PreviewService {

    String getOneUrl(long fileId);

    int addOnePicPreview(long fileId, String url);
}
