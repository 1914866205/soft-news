package com.soft1851.api.controller.files;

import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FileUploadControllerApi.java
 * @Description TODO
 * @createTime 2020年11月19日 15:07:00
 */
@Api(value = "文件上传controller", tags = {"文件上传controller"})
@RequestMapping("fs")
public interface FileUploadControllerApi {

    /**
     * 上传用户头像
     * @param userId 用户id
     * @param file 文件对象
     * @return封装结果
     * @throws Exception 异常
     */
    @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
    @PostMapping("uploadFace")
    GraceResult uploadFile(@RequestParam String userId, MultipartFile file) throws Exception;

}
