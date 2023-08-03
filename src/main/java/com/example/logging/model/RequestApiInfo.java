package com.example.logging.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestApiInfo {
  private ProceedingJoinPoint joinPoint;
  private ObjectMapper objectMapper;

  public RequestApiInfo(ProceedingJoinPoint joinPoint, ObjectMapper objectMapper) {
    this.joinPoint = joinPoint;
    this.objectMapper = objectMapper;
  }

  public String getUrl() {
    return this.getRequest().getRequestURL().toString();
  }

  public Map<String, Object> getHeaders() {
    Map<String, Object> map = new HashMap<>();

    Enumeration<String> em = this.getRequest().getHeaderNames();
    while (em.hasMoreElements()) {
      String name = em.nextElement();
      String value = this.getRequest().getHeader(name);

      map.put(name, value);
    }

    return map;
  }

  public String getMethod() {
    return this.getRequest().getMethod();
  }

  public Map<String, Object> getParameters() throws JsonProcessingException {
    Map<String, Object> map = new HashMap<>();
    Object[] args = joinPoint.getArgs();
    if (args.length > 0) {
      return (Map<String, Object>) args[0];
    } else {
      if (HttpMethod.GET.toString().equals(this.getMethod())) {
        for (String param : this.getRequest().getQueryString().split("&")) {
          String pair[] = param.split("=");
          if (pair.length > 1) {
            map.put(pair[0], pair[1]);
          } else {
            map.put(pair[0], "");
          }
        }

        return map;
      }
    }

    return null;
  }

  public String getIp() {
    HttpServletRequest request = this.getRequest();
    String ip = Objects.requireNonNull(request).getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  private HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }
}
