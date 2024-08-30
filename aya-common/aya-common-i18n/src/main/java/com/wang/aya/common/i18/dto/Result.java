package com.wang.aya.common.i18.dto;

import com.wang.aya.common.i18.util.ObjectUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.wang.aya.core.common.exception.StatusCode.OK;

/**
 * @author wangguangpeng
 * @date 2024/08/29 16:02
 **/
@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    private String traceId;

    private String spanId;

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(StatusCode.OK);
        result.setMsg(MessageUtil.getMessage(StatusCode.OK));
        return result;
    }

    public static <T> Result<T> fail(String code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(MessageUtil.getMessage(code));
        return result;
    }

    public static <T> Result<T> fail(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public boolean success() {
        return ObjectUtil.equals(this.code, StatusCode.OK);
    }

    public boolean error() {
        return !success();
    }

}
