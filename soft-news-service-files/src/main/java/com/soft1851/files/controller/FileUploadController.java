package com.soft1851.files.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.soft1851.api.controller.files.FileUploadControllerApi;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.FileUtil;
import com.soft1851.common.utils.extend.AliImageReviewUtil;
import com.soft1851.files.resource.FileResource;
import com.soft1851.files.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FileUploadController.java
 * @Description TODO
 * @createTime 2020年11月19日 15:13:00
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileUploadController implements FileUploadControllerApi {
    private final UploadService uploadService;
    private final FileResource fileResource;
    private final AliImageReviewUtil aliImageReviewUtil;
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFSBucket;


    /**
     * 检测不通过的默认图片
     */
    public static final String FAILED_IMAGE_URL = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3955828120,32488320&fm=26&gp=0.jpg";

    /**
     * @param userId 用户id
     * @param file   文件对象
     * @return
     * @throws Exception 文件上传，前端构建file文件对象，
     *                   那么后端客户获得MultipartFile对象，从中就能拿到file流，随后进行输出保存。
     *                   当然我们需要验证后缀名，如果不验证，那么非法用户会上传恶意脚本文件来攻击你的服务器。
     */
    @Override
    public GraceResult uploadFile(String userId, MultipartFile file) throws Exception {
        System.out.println("接口被访问");
        System.out.println(userId);
        System.out.println(file);
        String path;
        if (file != null) {
            //获得文件上传的名称
            String fileName = file.getOriginalFilename();
            //判断文件名不能为空
            if ((StringUtils.isNotBlank(fileName))) {
                //分割文件名
                String[] fileNameArr = fileName.split("\\.");
                //获得后缀
                String suffix = fileNameArr[fileNameArr.length - 1];
                //判断后缀符合我们的预定义规范
                if (!"png".equalsIgnoreCase(suffix) &&
                        !"jpg".equalsIgnoreCase(suffix) &&
                        !"jpeg".equalsIgnoreCase(suffix)) {
                    return GraceResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
                }
                // 执行上传服务，得到回调路径
//                path = uploadService.uploadFdfs(file, suffix);
                path = uploadService.uploadOSS(file, userId, suffix);
            } else {
                return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
            }
        } else {
            return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }
        log.info("path=" + path);
        String finalPath;
        if (StringUtils.isNotBlank(path)) {
            finalPath = fileResource.getOssHost() + path;
            log.info("finalPath=" + finalPath);
        } else {
            return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }
        return GraceResult.ok(doAliImageReview(finalPath));
//        return GraceResult.ok(finalPath);
    }

    /**
     * @param userId 用户id
     * @param files  文件数组
     * @return
     * @throws Exception
     */
    @Override
    public GraceResult uploadSomeFiles(String userId, MultipartFile[] files) throws Exception {
        //声明list，用于存放多个图片的地址路径，返回到前端
        List<String> imageUrlList = new ArrayList<>();
        System.out.println("files" + files.toString());
        System.out.println("userId" + userId);
        System.out.println(files != null);
        System.out.println(files.length);
        System.out.println(files.length > 0);
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String path;
                if (file != null) {
                    // 获得文件上传的名称
                    String fileName = file.getOriginalFilename();
                    System.out.println("fileName" + fileName);
                    //判断文件名不能为空
                    if (StringUtils.isNotBlank(fileName)) {
                        String[] fileNameArr = fileName.split("\\.");
                        //获得后缀
                        String suffix = fileNameArr[fileNameArr.length - 1];
                        //判断后缀符合我们的预定义规范
                        if (!"png".equalsIgnoreCase(suffix) &&
                                !"jpg".equalsIgnoreCase(suffix) &&
                                !"jpeg".equalsIgnoreCase(suffix)) {
                            continue;
                        }
                        //执行上传
                        path = uploadService.uploadOSS(file, userId, suffix);
                        System.out.println("path" + path);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
                String finalPath;
                if (StringUtils.isNotBlank(path)) {
                    finalPath = fileResource.getOssHost() + path;

                    System.out.println("finalPath" + finalPath);
                    imageUrlList.add(finalPath);
                }
            }
        }
        System.out.println("imageUrlList" + imageUrlList);
        return GraceResult.ok(imageUrlList);
    }

    /**
     * 阿里云图片智能检测
     *
     * @param pendingImageUrl 图片路径
     * @return
     */
    private String doAliImageReview(String pendingImageUrl) {
        log.info(pendingImageUrl);
        boolean result = false;
        try {
            result = aliImageReviewUtil.reviewImage(pendingImageUrl);
        } catch (Exception e) {
            System.err.println("图片识别出错");
        }
        if (!result) {
            return FAILED_IMAGE_URL;
        }
        return pendingImageUrl;
    }

    /**
     * @param username      管理员用户名
     * @param multipartFile 人脸照片文件
     * @return
     * @throws Exception
     */
    @Override
    public GraceResult uploadToGridFs(String username, MultipartFile multipartFile) throws Exception {
        Map<String, String> metaData = new HashMap<>(4);
        InputStream is = null;
        try {
            is = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件的源名称
        String fileName = multipartFile.getOriginalFilename();
        // 进行文件存储
        assert is != null;
        ObjectId objectId = gridFsTemplate.store(is, fileName, metaData);
        try {

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GraceResult.ok(objectId.toHexString());
    }

    @Override
    public GraceResult readInGridFs(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(faceId)));
        if (gridFSFile == null) {
            throw new RuntimeException("没有这个faceId:" + faceId);
        }
        System.out.println(gridFSFile.getFilename());
        //获取流对象
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        InputStream inputStream;
        String content = null;
        byte[] bytes = new byte[(int) gridFSFile.getLength()];
        try {
            inputStream = resource.getInputStream();
            inputStream.read(bytes);
            inputStream.close();
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GraceResult.ok(new String(bytes));
    }

    @Override
    public GraceResult readFace64(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 0.获得grids中的人脸文件
        File myFace = readFileFromGridFs(faceId);
        // 1.转换人脸为base64
        String base64Face = FileUtil.fileToBase64(myFace);
        return GraceResult.ok(base64Face);
    }

    private File readFileFromGridFs(String faceId) throws Exception {
        System.out.println("readFileFromGridFs接收的faceid"+faceId);
        GridFSFindIterable files = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));
        GridFSFile gridFSFile = files.first();
        if (gridFSFile == null) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }
        String fileName = gridFSFile.getFilename();
        System.out.println(fileName);

        //获取文件流，保存文件到本地货服务器的临时目录
        File fileTemp = new File("D:\\360MoveData\\Users\\lenovo\\Desktop");
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
        File myFile = new File("D:\\360MoveData\\Users\\lenovo\\Desktop\\" + fileName);
        //创建文件输出流
        OutputStream os = new FileOutputStream(myFile);
        //下载到服务器或者本地
        gridFSBucket.downloadToStream(new ObjectId(faceId), os);
        return myFile;
    }
}
