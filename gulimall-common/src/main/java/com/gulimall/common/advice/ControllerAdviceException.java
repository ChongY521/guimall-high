package com.gulimall.common.advice;

import com.gulimall.common.exception.BusinessException;
import com.gulimall.common.exception.CommonErrorCode;
import com.gulimall.common.exception.ErrorCode;
import com.gulimall.common.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aqiang9  2020-07-28
 */
@RestControllerAdvice(basePackages = "com.gulimall.*.controller") // 处理所有异常信息
@Slf4j
public class ControllerAdviceException {
    /**
     * 处理 数据校验 错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Map<String,String>> validFailException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, String> errorMap = new HashMap<>(fieldErrors.size());
        fieldErrors.forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        log.error("数据校验错误 , 错误信息 {}", errorMap);
        return CommonResult.fail(CommonErrorCode.VALID_FAIL_EXCEPTION , errorMap );
    }
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult businessExceptionHandler(BusinessException businessException) {
        ErrorCode errorCode = businessException.getErrorCode();
        log.error("业务异常, 异常码：{} , 异常信息：{}", errorCode.getCode(), errorCode.getMsg());
        return CommonResult.fail(errorCode);
    }

    /**
     * 处理所有异常
     */
//    @ExceptionHandler(Exception.class)
    public CommonResult allExceptionHandler(Exception e) {
        log.error("出现异常,异常信息 :{}", e.getMessage());

        return CommonResult.fail(CommonErrorCode.SYSTEM_UNKNOWN_EXCEPTION).data(e.getMessage());
    }
}
