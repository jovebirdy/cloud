package com.jxph.cloud.service.fast.server.common.exception;

import com.jxph.cloud.common.enums.ResultCodeEnum;
import com.jxph.cloud.common.exception.AuthorizationException;
import com.jxph.cloud.common.exception.BaseException;
import com.jxph.cloud.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 谢秋豪
 * @date 2018/8/18 23:07
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        log.error(e.getMessage(), e);
        return "";
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResponseResult authorizationException(HttpServletResponse response, AuthorizationException e) {
        response.setStatus(ResultCodeEnum.FAILURE.getCode());
        log.error(e.getMsg(), e);
        return new ResponseResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseResult baseException(HttpServletResponse response, BaseException e) {
        response.setStatus(ResultCodeEnum.FAILURE.getCode());
        log.error(e.getMsg(), e);
        return new ResponseResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseResult validationException(HttpServletResponse response, BindException error) {
        response.setStatus(ResultCodeEnum.FAILURE.getCode());
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = error.getFieldErrors();
        errors.forEach(fieldError -> {
            errorMessage.append(fieldError.getDefaultMessage());
        });
        return ResponseResult.error(errorMessage.toString());
    }
}
