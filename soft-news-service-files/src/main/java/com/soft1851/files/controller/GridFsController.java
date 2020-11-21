package com.soft1851.files.controller;


import com.mongodb.client.gridfs.model.GridFSFile;
import com.soft1851.common.result.GraceResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName GridFsController.java
 * @Description TODO
 * @createTime 2020年11月22日 00:50:00
 */
@RestController
@RequestMapping("/gridfs")
public class GridFsController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 上传成功文件id
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        // 设置meta数据值
        Map<String, String> metaData = new HashMap<>(8);
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件的源名称
        String fileName = multipartFile.getOriginalFilename();
        // 进行文件存储
        assert inputStream != null;
        ObjectId objectId = gridFsTemplate.store(inputStream, fileName, metaData);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectId.toHexString();
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件id
     */
    @GetMapping("/get/{fileId}")
    public void getFile(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (gridFSFile == null) {
            throw new RuntimeException("No file with id: " + fileId);
        }
        System.out.println(gridFSFile.getFilename());
        //获取流对象
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        InputStream inputStream;
        String content = null;
        byte[] bytes = new byte[(int) gridFSFile.getLength()];
        try {
            inputStream = resource.getInputStream();
            int read = inputStream.read(bytes);
            inputStream.close();
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();
            ////获取流中的数据
            //content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ////获取byte[]信息
            //bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param fileId 文件id
     */
    @DeleteMapping("/delete")
    public GraceResult deleteFile(@RequestParam(value = "fileId") String fileId) {
        // 根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
        return GraceResult.ok();
    }
}
