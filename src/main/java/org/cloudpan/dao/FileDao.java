package org.cloudpan.dao;

import org.apache.ibatis.annotations.Param;
import org.cloudpan.entity.File;

/**
 * Created by TracyM on 2017/3/22.
 */
public interface FileDao {

    File queryByMd5(@Param("fileMd5") String fileMd5);

    /**
     *
     * @param filePath
     * @param fileMd5
     * @param fileSize
     */
    int addFile(@Param("filePath") String filePath, @Param("fileMd5") String fileMd5, @Param("fileSize") long fileSize);

    File queryById(@Param("fileId") long fileId);

    long querySizeById(@Param("fileId") long fileId);


}
