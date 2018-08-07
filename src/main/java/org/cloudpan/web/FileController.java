package org.cloudpan.web;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.apache.commons.io.FileUtils;
import org.cloudpan.dto.ShareInfo;
import org.cloudpan.dto.PreviewInfo;
import org.cloudpan.dto.UserInfo;
import org.cloudpan.entity.Record;
import org.cloudpan.entity.User;
import org.cloudpan.service.FileService;
import org.cloudpan.service.PreviewService;
import org.cloudpan.service.RecordService;
import org.cloudpan.service.UserService;
import org.cloudpan.service.impl.FileServiceImpl;
import org.cloudpan.service.impl.PreviewServiceImpl;
import org.cloudpan.service.impl.RecordServiceImpl;
import org.cloudpan.service.impl.UserServiceImpl;
import org.cloudpan.util.CosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;


/**
 * Created by TracyM on 2017/3/23.
 */

@Controller
@RequestMapping("/file")
public class FileController {

    private Long userId;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //盐值用于混肴md5字符串
    private String salt = "d^F&3rOpU*Dh`%32xGPw&mSQtUn)";

    @Autowired
    private FileService fileService = new FileServiceImpl();

    @Autowired
    private RecordService recordService = new RecordServiceImpl();

    @Autowired
    private UserService userService = new UserServiceImpl();

    @Autowired
    private PreviewService previewService = new PreviewServiceImpl();

    /**
     * 请求jsp页面资源
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public String list(Model model,HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        User user = new User();
        UserInfo userInfo= (UserInfo) httpSession.getAttribute("UserInfo");
        if (userInfo==null){
            logger.info("UserInfo==null");
            return "redirect:http://localhost:8080";
        }
        this.userId = userService.queryUserId(userInfo.getUserPhone());
        //获取列表页
        List<Record> list = recordService.getRecordList(userId);
        model.addAttribute("list",list);//Model

        logger.info("userId="+userId);
        return "list";
    }

    @RequestMapping(value = "/upload",method=RequestMethod.POST)
    @ResponseBody
    //name和addrinfo是表单提交的数据 因为文件上传有可能带有其他参数   但是名字要与表单里的名字一样
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
        String fileMd5, Short share, Date createTime, String customName,HttpServletRequest request) throws IOException {

        COSClient cosClient = CosUtil.getCred();

        String fileName = file.getOriginalFilename(); //获取文件名
        long fileSize = file.getSize();//获取文件大小

//        File uploadFile = new File("D://temp");
//        file.transferTo(uploadFile);

        int status=fileService.addFile(fileMd5,userId,fileName,customName,share,createTime,fileSize);
        switch (status){
            case 0:{//文件不存在，先储存文件
                String bucketName = "test-1252882699";
                String key = "/"+fileName;
                try {
                    ObjectMetadata objectMetadata = cosClient.getObjectMetadata(bucketName, key);
                } catch (CosClientException e) {

                }

                if (!file.isEmpty()) {
                    String path = "C://upload";
                    File dir = new File(path, fileName);

                    logger.info(userId+"--"+customName +"--" +fileMd5 +"--"+ share +"--"+ createTime);



                    if(!dir.exists()){
                        //原方法
                        dir.mkdirs();
                    }
                    //MultipartFile自带解析方法
                    try {
                        file.transferTo(dir);
                    } catch (IOException e) {
                        logger.debug("文件储存到磁盘过程中出现异常");
                        //TODO-----储存文件过程中发生异常，要求将插入file表中的记录删除
                        e.printStackTrace();
                    }

                    // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
                    // 大文件上传请参照 API 文档高级 API 上传
                    //File localFile = new File("D:\\申请表.pdf");
                    // 指定要上传到 COS 上的路径
                    String filekey = "/"+fileName;
                    String bucketName1 = CosUtil.getBucketName();
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName1, filekey, dir);
                    PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

                    // 关闭客户端(关闭后台线程)
                    cosClient.shutdown();

                    //两次调用addfile()方法
                    fileService.addFile(fileMd5, userId, fileName, customName, share, createTime, fileSize);
                }
                return "ok";
            }
            case 1:{
                return "ok";
            }
            default:
                return "not ok";
        }
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(long fileId,
        String customName,HttpServletRequest request) throws IOException {

        HttpSession httpSession=request.getSession();
        User user= (User) httpSession.getAttribute("User");

        String fileName = fileService.getFilePath(fileId);
        String path =fileName;
        File file=new File(path);
        HttpHeaders headers = new HttpHeaders();
        String downloadFile=new String(customName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", downloadFile);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    @RequestMapping("/{recordId}/rename")
    @ResponseBody
    public String rename(@PathVariable("recordId") long recordId,String customName,short share){
        recordService.updateCustomName(recordId, customName);
        recordService.updateShare(recordId, share);
        return "ok";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long recordId){
        logger.info(recordId+"");
        recordService.deleteRecord(recordId);
        return "ok";
    }

    public String generateUrl(long fileId){
        String fileMd5=fileService.getFileMd5(fileId);
        return fileMd5;
    }


    @RequestMapping(value="/downloadurl",produces = "text/html")
    @ResponseBody
    public String getUrl(long fileId,String customName,long recordId){
        logger.info(fileId + "-" + customName + "-" + recordId);
        return "ok";
    }

    //生成文件分享页面的url
    @RequestMapping(value = "/share",produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public ShareInfo generateSharePageUrl(long recordId){

        Record record = recordService.getRecordById(recordId);
        ShareInfo shareInfo = new ShareInfo();
        String md5 = fileService.getFileMd5(record.getFileId());

        //貌似只有这两个有用？上面貌似不需要
        shareInfo.setRecordMd5(record.getRecordMd5());
        shareInfo.setSuccess(true);

        if (record.getShare()==0){
            recordService.updateShare(recordId, ((short) 1));
            return shareInfo;
        }
        else if (record.getShare()==1){
            return shareInfo;
        }else {
            shareInfo.setSuccess(false);
            return shareInfo;
        }
    }

    //生成分享页面的信息
    @RequestMapping("/share/{recordMd5}")
    public String sharePage(@PathVariable("recordMd5") String recordMd5,Model model,HttpServletRequest request){
        HttpSession httpSession=request.getSession();

        Record record = recordService.getRecordByMd5(recordMd5);
        //设定session过期的时间（下载链接过期的时间）单位：秒
        httpSession.setMaxInactiveInterval(10);
        httpSession.setAttribute("record", record);

        ShareInfo shareInfo = new ShareInfo();

        shareInfo.setCreateTime(record.getCreateTime());
        shareInfo.setCustomName(record.getCustomName());
        shareInfo.setFileId(record.getFileId());
        shareInfo.setUserId(record.getUserId());
        shareInfo.setRecordId(record.getRecordId());
        shareInfo.setShare(record.getShare());
        shareInfo.setRecordMd5(record.getRecordMd5());
        shareInfo.setFileSize(fileService.getFileSize(record.getFileId()));
        shareInfo.setUserName(userService.getUserNameById(record.getUserId()));
        shareInfo.setSuccess(true);

        model.addAttribute("shareInfo", shareInfo);
        return "share";
    }

    //根据md5下载文件
    @RequestMapping("/download/{recordMd5}")
    public ResponseEntity<byte[]> shareDownload(@PathVariable("recordMd5")String recordMd5, HttpServletRequest request) throws IOException {

        HttpSession httpSession=request.getSession();
        Record record= (Record) httpSession.getAttribute("record");

        logger.debug("record"+record);
        if (record!=null){
            String fileName = fileService.getFilePath(record.getFileId());
            String path =fileName;
            File file=new File(path);
            HttpHeaders headers = new HttpHeaders();
            String downloadFile=new String(record.getCustomName().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", downloadFile);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        }

        else {
            return new ResponseEntity<byte[]>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
    }

    //中转方法，将请求转发到相应的方法中
    @RequestMapping("/test/{recordMd5}")
    public String getTestPreview(@PathVariable("recordMd5") String recordMd5){
        PreviewInfo previewInfo = new PreviewInfo();
        Record record = recordService.getRecordByMd5(recordMd5);
//        if (record!=null){
//            previewInfo.setType(record.getFileType());
//        }
        if (record.getFileType()==2){
            return "forward:/file/pic/" + recordMd5;
        }else if (record.getFileType()==1){
            return "forward:/file/text/" + recordMd5;
        }else
            return "forward:/file/text/" + recordMd5;
    }

    @RequestMapping("/text/{recordMd5}")
    @ResponseBody
    public PreviewInfo getTextPreview(@PathVariable("recordMd5") String recordMd5){
//        StringBuffer sb = new StringBuffer("");
//        org.cloudpan.entity.File file = new org.cloudpan.entity.File();

        String filePath = fileService.getFilePath(recordService.getRecordByMd5(recordMd5).getFileId());
        File file = new File(filePath);

        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String text = new String(sb); //StringBuffer ==> String
        System.out.println("文本为==> " + text);
        logger.debug(text);

        PreviewInfo previewInfo = new PreviewInfo();
        previewInfo.setText(text);
        previewInfo.setHasNext(false);
        previewInfo.setType(((short) 1));

        return previewInfo;
    }

    @RequestMapping("/pic/{recordMd5}")
    @ResponseBody
    public PreviewInfo getPicPreview(@PathVariable("recordMd5") String recordMd5){
        Record record = recordService.getRecordByMd5(recordMd5);
        org.cloudpan.entity.File file=fileService.getFileById(record.getFileId());
        String url = previewService.getOneUrl(file.getFileId());

        PreviewInfo previewInfo = new PreviewInfo();
        previewInfo.setText(url);
        previewInfo.setType(record.getFileType());

        return previewInfo;
    }

}
