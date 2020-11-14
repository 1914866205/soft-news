package com.soft1851.result;

import java.util.Map;

/**
 * @description: 自定义优雅的响应数据结构
 * @author: mqxu
 * @create: 2020-11-14
 **/
public class GraceResult {

    /**
     * 响应业务状态码
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 响应数据，可以是Object，也可以是List或Map等
     */
    private Object data;

    /**
     * 成功返回带有数据的结果
     *
     * @param data： 入参
     * @return GraceResult: 返回结果
     */
    public static GraceResult ok(Object data) {
        return new GraceResult(data);
    }

    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入
     *
     * @return GraceResult: 返回结果
     */
    public static GraceResult ok() {
        return new GraceResult(ResponseStatusEnum.SUCCESS);
    }

    public GraceResult(Object data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     *
     * @return GraceResult: 返回结果
     */
    public static GraceResult error() {
        return new GraceResult(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的结果返回出去
     *
     * @param map： 多条错误信息等map
     * @return GraceResult: 返回结果
     */
    public static GraceResult errorMap(Map map) {
        return new GraceResult(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     *
     * @param msg： 错误消息
     * @return GraceResult: 返回结果
     */
    public static GraceResult errorMsg(String msg) {
        return new GraceResult(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     *
     * @return GraceResult: 返回结果
     */
    public static GraceResult errorTicket() {
        return new GraceResult(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java中自定义后再传入
     *
     * @param responseStatus： 响应枚举
     * @return GraceResult: 返回结果
     */
    public static GraceResult errorCustom(ResponseStatusEnum responseStatus) {
        return new GraceResult(responseStatus);
    }

    public static GraceResult exception(ResponseStatusEnum responseStatus) {
        return new GraceResult(responseStatus);
    }

    public GraceResult(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
    }

    public GraceResult(ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
        this.data = data;
    }

    public GraceResult(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.success = responseStatus.success();
    }

    public GraceResult() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
