package com.soft1851.files.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
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
    public final Sid sid;

    /**
     *
     * @param file 文件
     * @param fileExtName 扩展名
     * @return
     * @throws Exception
     *
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
}
