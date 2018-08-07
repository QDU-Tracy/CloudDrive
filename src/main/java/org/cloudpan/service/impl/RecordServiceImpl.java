package org.cloudpan.service.impl;

import org.cloudpan.dao.RecordDao;
import org.cloudpan.entity.Record;
import org.cloudpan.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TracyM on 2017/3/27.
 */

@Service
@Transactional
public class RecordServiceImpl implements RecordService{

    @Autowired
    private RecordDao recordDao;

    @Override
    public List<Record> getRecordList(long userId) {
        return recordDao.queryByUserId(userId, 0, 10);
    }

    @Override
    public int deleteRecord(long recordId) {
        recordDao.deleteRecord(recordId);
        return 0;
    }

    @Override
    public int updateCustomName(long recordId, String customName) {
        recordDao.updateCustomName(recordId,  customName);
        return 0;
    }

    @Override
    public int updateShare(long recordId, short share) {
        recordDao.updateShare(recordId, share);
        return 0;
    }

    @Override
    public Record getRecordById(long recordId) {
        Record record=recordDao.queryByRecordId(recordId);
        return record;
    }

    @Override
    public Record getRecordByMd5(String recordMd5) {
        Record record=recordDao.queryByRecordMd5(recordMd5);
        return record;
    }
}
