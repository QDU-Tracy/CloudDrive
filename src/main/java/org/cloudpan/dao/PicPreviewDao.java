package org.cloudpan.dao;

import org.apache.ibatis.annotations.Param;

public interface PicPreviewDao {
    String queryOneByFileId(@Param("fileId") long fileId);

    int addPicPreview(@Param("fileId") long fileId, @Param("url") String url);
}
