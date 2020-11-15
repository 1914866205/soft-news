package com.soft1851.common.exception;

import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 统一异常拦截处理
 * 可以针对异常的类型进行捕获，然后返回json信息到前端
 * @author: mqxu
 * @create: 2020-11-15
 */
@ControllerAdvice
public class GraceExceptionHandler {

    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public GraceResult returnMyException(MyCustomException e) {
        e.printStackTrace();
        return GraceResult.exception(e.getResponseStatusEnum());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public GraceResult returnMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return GraceResult.errorCustom(ResponseStatusEnum.FILE_MAX_SIZE_ERROR);
    }

}
