package org.cloudpan.service.impl;

import net.coobird.thumbnailator.Thumbnails;
import org.cloudpan.dao.FileDao;
import org.cloudpan.dao.PicPreviewDao;
import org.cloudpan.dao.RecordDao;
import org.cloudpan.entity.File;
import org.cloudpan.service.FileService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by TracyM on 2017/3/23.
 */

@Service
@Transactional
public class FileServiceImpl implements FileService {
    private static  String URL = "http://localhost:8080/cdn1/";

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    //盐值用于混肴md5字符串
    private String salt = "d^F&3rOpU*Dh`%32xGPw&mSQtUn)";

    @Autowired
    private FileDao fileDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private PicPreviewDao picPreviewDao;


    @Override
    public int addRecord(long userId,long fileId,String customName,short share,Date createTime,String recordMd5,short fileType) {
        //i:受影响行数
        int i=recordDao.addRecord(userId,fileId,customName,share,createTime,recordMd5,fileType);
        logger.info(i+"");
        return 0;
    }

    @Override
    public String getFilePath(long fileId) {
//        long fileId=recordDao.queryFileId(recordId).getFileId();
        String fileName = fileDao.queryById(fileId).getFilePath();
        return fileName;
    }

    @Override
    public String getFileMd5(long fileId) {
        String fileMd5 = fileDao.queryById(fileId).getFileMd5();
        return fileMd5;
    }

    @Override
    public long getFileSize(long fileId) {
        long fileSize = fileDao.querySizeById(fileId);
        return fileSize;
    }

    @Override
    public File getFileByMd5(String fileMd5) {
        return fileDao.queryByMd5(fileMd5);
    }

    @Override
    public File getFileById(long fileId) {
        return fileDao.queryById(fileId);
    }

    //此方法包括两个操作，添加到file表和添加到record表都在此方法实现，否则业务逻辑需要在Controller层完成
    @Override
    public int addFile(String fileMd5,long userId,String fileName,String customName,
                       short share,Date createTime,long fileSize) {
        //通过MD5查询到file
        File file=fileDao.queryByMd5(fileMd5);

        //file表中不存在这个文件，先在file表中加入记录，然后要求储存在磁盘中
        if (file==null){
            String filePath = "C://upload//" + fileName;
            fileDao.addFile(filePath,fileMd5,fileSize);
            return 0;
        }

        //file表中已经存在这个文件,将记录插入record表
        else {
            String base = fileMd5+"/"+salt;
            String recordMd5 = DigestUtils.md5DigestAsHex(base.getBytes());

            short fileType;//文件类型

            //图片类型数组
            String[] pic = new String[]{".jpg", ".png", ".gif", ".bmp",".JPG",".PNG",".GIF",".BMP"};
            //文本类型数组
            String[] text = new String[]{".txt",".TXT"};

            if (isType(fileName,pic)==true){
                fileType = 2;
                String picFilePath = fileDao.queryByMd5(fileMd5).getFilePath();
                java.io.File picFile = new java.io.File(picFilePath);

                //开始组装图片url,此时后缀名还没有确认，将在if中组装
                String url = URL + fileMd5 + "_preview";

                try {
                    /**
                     * 指定大小进行缩放
                     * 若图片横比200小，高比300小，不变
                     * 若图片横比200小，高比300大，高缩小到300，图片比例不变
                     * 若图片横比200大，高比300小，横缩小到200，图片比例不变
                     * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
                     */
                    BufferedImage originalImage = ImageIO.read(picFile);
                    BufferedImage thumbnail = Thumbnails.of(originalImage)
                            .size(200, 300)
                            .asBufferedImage();
                    if (customName.endsWith("JPG")||customName.endsWith("jpg")){
                        ImageIO.write(thumbnail, "jpg", new java.io.File("C://upload//picPreview//" + fileMd5 + "_preview.jpg"));
                        url = url + ".jpg";
                    }else if (customName.endsWith("PNG")||customName.endsWith("png")){
                        ImageIO.write(thumbnail, "png", new java.io.File("C://upload//picPreview//" + fileMd5 + "_preview.png"));
                        url = url + ".png";
                    }else {
                        //TODO 不支持的格式
                        ImageIO.write(thumbnail, "jpg", new java.io.File("C://upload//picPreview//" + fileMd5 + "_preview.jpg"));
                        url = url + ".jpg";
                    }
                } catch (IOException e) {
                    //TODO 缩略图异常处理
                    e.printStackTrace();
                } finally {
                    picPreviewDao.addPicPreview(file.getFileId(), url);
                }
            }else if (isType(fileName,text)){
                fileType = 1;
            }else {
                fileType = 0;
            }

            recordDao.addRecord(userId,file.getFileId(),customName,share,createTime,recordMd5,fileType);
            return 1;
        }
    }

    public static boolean isType(String contentType, String... allowTypes) {
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.indexOf(type) > -1) {
                return true;
            }
        }
        return false;
    }
}
