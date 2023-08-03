package com.example.logging.common.exception.advice;

import com.example.logging.common.exception.BizException;
import com.example.logging.common.response.ResultData;
import com.example.logging.common.response.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {

  @ExceptionHandler(BizException.class)
  public ResponseEntity<ResultData<?>> handleBizException(HttpServletRequest req, BizException e) {
    log.error("BIZ_EXCEPTION : {} | {}", req.getRequestURI(), e.getMessage());
    Status status = e.getStatus();
    return new ResponseEntity<ResultData<?>>(ResultData.of(status, null), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
