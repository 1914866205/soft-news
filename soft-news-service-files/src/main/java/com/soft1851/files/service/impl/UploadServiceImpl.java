package com.soft1851.files.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.soft1851.common.utils.extend.AliyunResource;
import com.soft1851.files.resource.FileResource;
import com.soft1851.files.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UploadServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月19日 14:54:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadServiceImpl implements UploadService {

    public final FastFileStorageClient fastFileStorageClient;
    public final FileResource fileResource;
    public final AliyunResource aliyunResource;
    public final Sid sid;

    /**
     * @param file        文件
     * @param fileExtName 扩展名
     * @return
     * @throws Exception
     */
    @Override
    public String uploadFdfs(MultipartFile file, String fileExtName) throws Exception {
        InputStream inputStream = file.getInputStream();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream,
                file.getSize(),
                fileExtName,
                null);
        inputStream.close();
        return storePath.getFullPath();
    }

    @Override
    public String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception {
        String endpoint = fileResource.getEndpoint();
        String accessKeyId = aliyunResource.getAccessKeyId();
        String accessKeySecret = aliyunResource.getAccessKeySecret();
        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String fileName = sid.nextShort();
        String myObjectName = fileResource.getObjectName() + fileName + "." + fileExtName;
        //上传网络流
        InputStream inputStream = file.getInputStream();
        ossClient.putObject(fileResource.getBucketName(),
                myObjectName, inputStream);
        //关闭OSSClient
        ossClient.shutdown();
        return myObjectName;
    }
}
