package org.cloudpan.dto;

import org.apache.commons.io.FileUtils;
import org.cloudpan.service.impl.FileServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by TracyM on 2017/3/23.
 */
public class FileOperation {
    /**
     * 计算文件的MD5码
     *
     * @param file
     * @return
     */
    public String getFileMd5(File file) {

        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            System.out.println("开始算");
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte m[ ]=md.digest( );

            //将计算的结果由字节数组转换到string储存到str中
            StringBuffer strBuffer = new StringBuffer();
            for (int i = 0; i < m.length; i++) {
                strBuffer.append(Integer.toHexString(0xff & m[i]));
            }

            System.out.println("算完了"+strBuffer.toString());
            return strBuffer.toString();
        } catch (IOException ex) {
            Logger.getLogger(FileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(FileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public long getFileSize(File file){
        return FileUtils.sizeOf(file);
    }


}
