package org.cloudpan.dao;

import org.apache.ibatis.annotations.Param;
import org.cloudpan.entity.Record;

import java.util.Date;
import java.util.List;

/**
 * Created by TracyM on 2017/3/22.
 */

public interface RecordDao {

    int addRecord(@Param("userId") long userId, @Param("fileId") long fileId,
                   @Param("customName") String customName, @Param("share") short share,
                   @Param("createTime") Date createTime,@Param("recordMd5") String recordMd5,
                  @Param("fileType") short fileType);


    int updateCustomName(@Param("recordId") long recordId, @Param("customName") String customName);


    /**
     * 根据偏移量查询全部记录
     * @param offset
     * @param limit
     * @return
     */
    List<Record> queryAll(@Param("offset")int offset, @Param("limit")int limit);


    int deleteRecord(@Param("recordId") long recordId);

    List<Record> queryByUserId(@Param("userId") long UserId,@Param("offset")int offset, @Param("limit")int limit);

    int updateShare(@Param("recordId") long recordId, @Param("share") short share);

    Record queryByRecordId(@Param("recordId") long recordId);

    Record queryByRecordMd5(@Param("recordMd5") String recordMd5);

}
