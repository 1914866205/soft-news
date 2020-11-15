package com.soft1851.common.exception;

import com.soft1851.common.result.ResponseStatusEnum;

/**
 * 自定义异常
 * 便于解耦，service与controller错误的解耦，不会被service返回的类型而限制
 *
 * @author: mqxu
 * @create: 2020-11-15
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为：" + responseStatusEnum.status()
                + "；具体异常信息为：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
