package com.soft1851.files.controller;

import com.soft1851.api.controller.files.FileUploadControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.extend.AliImageReviewUtil;
import com.soft1851.files.resource.FileResource;
import com.soft1851.files.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
                path = uploadService.uploadOSS(file, userId,suffix);
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
}
