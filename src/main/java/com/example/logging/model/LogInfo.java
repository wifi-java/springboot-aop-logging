package com.example.logging.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class LogInfo {
  private String url;
  private String method;
  private Map<String, Object> headers;
  private Map<String, Object> parameters;
  private Object response;
  private String ip;
  private String exception;

  public LogInfo(String url, String method, Map<String, Object> headers, Map<String, Object> parameters, String ip) {
    this.url = url;
    this.method = method;
    this.headers = headers;
    this.parameters = parameters;
    this.ip = ip;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public void setResponse(Object response) {
    this.response = response;
  }
}
