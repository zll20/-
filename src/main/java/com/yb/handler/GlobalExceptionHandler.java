package com.yb.handler;

import com.yb.utils.R;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 专门处理客户端中断连接异常
     * 避免大文件上传中断时打印长篇报错
     */
    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e) {
        // 忽略此异常，不做任何处理
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        // 如果是客户端中断异常，直接返回 null
        if (e instanceof ClientAbortException) {
            return null;
        }
        
        logger.error("System Error: ", e);
        return R.error("请求地址 " + request.getRequestURI() + " 发生系统异常：" + e.getMessage());
    }
}
