package com.soft1851.files.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UploadService.java
 * @Description 文件上传服务接口
 * @createTime 2020年11月19日 14:45:00
 */
public interface UploadService {

    /**
     * fdfs上传
     * @param file 文件
     * @param fileExtName 扩展名
     * @return
     * @throws Exception 异常
     */
    String uploadFdfs(MultipartFile file, String fileExtName) throws Exception;
}
