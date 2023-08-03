package com.example.logging.controller;

import com.example.logging.common.response.ResultData;
import com.example.logging.common.response.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SampleController {

  @PostMapping(path = "/test")
  public ResultData<?> test(@RequestBody Map<String, Object> map) {
    log.info("info");
    log.debug("debug");
    log.error("error");
    return ResultData.of(Status.SUCCESS);
  }
}
