package org.cloudpan.service.impl;

import org.cloudpan.dao.PicPreviewDao;
import org.cloudpan.service.PreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PreviewServiceImpl implements PreviewService {

    @Autowired
    private PicPreviewDao picPreviewDao;

    @Override
    public String getOneUrl(long fileId) {
        return picPreviewDao.queryOneByFileId(fileId);
    }

    @Override
    //插入成功返回1，有已存在记录返回0
    public int addOnePicPreview(long fileId, String url) {
        return picPreviewDao.addPicPreview(fileId, url);
    }
}
