package org.cloudpan.service;

import org.apache.commons.io.FileUtils;
import org.cloudpan.service.impl.FileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by TracyM on 2017/3/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class FileTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;


//    @Test
//    public void FileTest(){
//        String path = "F:\\test.py";
//        File file = FileUtils.getFile(path);
//        fileService.addFile(path,"B26ABFF898DE9E279B9BCE2B4C2ADD3",55555);
//
////        String path1 = "F:\\test.py";
////        String path2 = "F:\\test1.avi";
////        File file1 = FileUtils.getFile(path1);
////        File file2 = FileUtils.getFile(path2);
////        try {
////            boolean a=FileUtils.contentEquals(file1, file2);
////            System.out.println(a);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
    @Test
    public void TestInsertRecord(){
        Date date = new Date();
//          //下面是转为sql的TimeStamp类型，实际上不需要转换
//        Long dateLong = date.getTime();
//        Timestamp timestamp = new Timestamp(dateLong);
        fileService.addRecord(4, 9, "xxx.avi", (short) 1, date, "xxx", ((short) 0));
    }

    @Test
    public void TestGetTxtPreview(){

    }

    @Test
    public void TestAddFile(){

    }
}
