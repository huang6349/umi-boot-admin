package org.umi.boot.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.umi.boot.commons.exception.InternalServerErrorException;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.commons.utils.ThrowableUtil;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @Autowired
    private MultipartProperties multipartProperties;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public InfoStructure handleThrowable(final Throwable throwable) {
        log.error(ThrowableUtil.getStackTrace(throwable));
        return Info.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), throwable.getMessage(), throwable.getClass().getName());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleBindException(final BindException e) {
        return Info.error(HttpStatus.BAD_REQUEST.toString(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e.getClass().getName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return Info.error(HttpStatus.BAD_REQUEST.toString(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e.getClass().getName());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        return Info.error(HttpStatus.BAD_REQUEST.toString(), StrUtil.format("上传失败，文件大小超出了最大限制（{}MB）", multipartProperties.getMaxFileSize().toMegabytes()), e.getClass().getName());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleBadCredentialsException(final BadCredentialsException e) {
        return Info.error(HttpStatus.UNAUTHORIZED.toString(), "您输入的帐号或者密码不正确，请重试", e.getClass().getName());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        return Info.error(HttpStatus.UNAUTHORIZED.toString(), e.getMessage(), e.getClass().getName());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InfoStructure handleInternalServerErrorException(final InternalServerErrorException e) {
        return Info.error(e.getErrorCode(), e.getMessage(), e.getClass().getName(), e.getShowType(), e.getTraceId(), e.getHost());
    }
}
