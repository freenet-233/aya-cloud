package com.wang.aya.core.handler;

import com.wang.aya.common.i18.dto.Result;
import com.wang.aya.common.i18.util.ObjectUtil;
import jakarta.validation.ValidationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常统一处理
 * @author wangguangpeng
 * @date 2024/08/29 16:00
 **/
public class GlobalExceptionHandler {
    /**
     * 异常处理并响应.
     * @param ex 异常
     * @return 响应结果
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class, ValidationException.class })
    public Result<?> handle(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException mane) {
            FieldError fieldError = mane.getFieldError();
            if (ObjectUtil.isNotNull(fieldError)) {
                return Result.fail(fieldError.getCode(), fieldError.getDefaultMessage());
            }
        }
        throw new RuntimeException();
    }
}
