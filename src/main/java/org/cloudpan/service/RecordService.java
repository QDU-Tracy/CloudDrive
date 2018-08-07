package org.cloudpan.service;

import org.cloudpan.entity.Record;

import java.util.List;

/**
 * Created by TracyM on 2017/3/27.
 */
public interface RecordService {
    List<Record> getRecordList(long userId);

    int deleteRecord(long recordId);

    int updateCustomName(long recordId, String customName);

    int updateShare(long recordId, short share);

    Record getRecordById(long recordId);

    Record getRecordByMd5(String recordMd5);

}
