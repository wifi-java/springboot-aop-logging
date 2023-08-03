package com.example.logging.aop;

import com.example.logging.common.exception.BizException;
import com.example.logging.model.LogInfo;
import com.example.logging.model.RequestApiInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
  private final ObjectMapper objectMapper;

  // 모든 컨트롤러 대상으로 로그 수집
  @Pointcut("within(*..*Controller)")
  public void onRequest() { }

  @Around("onRequest()")
  public Object requestLogging(ProceedingJoinPoint joinPoint) throws Throwable {
    // 리퀘스트 정보를 담을 객체
    final RequestApiInfo apiInfo = new RequestApiInfo(joinPoint, objectMapper);

    // 로그 적재할 객체
    final LogInfo logInfo = new LogInfo(
            apiInfo.getUrl(),
            apiInfo.getMethod(),
            apiInfo.getHeaders(),
            apiInfo.getParameters(),
            apiInfo.getIp()
    );

    try {
      final Object result = joinPoint.proceed(joinPoint.getArgs());
      // 호출 결과를 로그 적재할 객체에 셋팅
      logInfo.setResponse(result);

      // 로그를 json 으로 변환하여 출력
      final String logMessage = objectMapper.writeValueAsString(Map.entry("logInfo", logInfo));
      logger.debug(logMessage);

      return result;
    } catch (Exception e) {
      // 예외가 발생했을때 로그 적재
      if (e instanceof BizException) {
        StringBuilder builder = new StringBuilder(e.toString());
        Exception exception = ((BizException) e).getException();
        if (exception != null) {
          builder.append(" || ");
          builder.append(exception.toString());
        }

        logInfo.setException(builder.toString());
      } else {
        final StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logInfo.setException(sw.toString());
      }

      final String logMessage = objectMapper.writeValueAsString(logInfo);
      logger.error(logMessage);

      throw e;
    }
  }
}
