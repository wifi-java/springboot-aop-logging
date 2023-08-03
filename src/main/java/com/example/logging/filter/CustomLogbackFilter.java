package com.example.logging.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class CustomLogbackFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        // 해당 로거 이름으로 찍히는 로그는 파일에 쓰지 않도록 예외처리.
        if ("com.example.logging.aop.LoggingAspect".equals(event.getLoggerName())) {
            return FilterReply.DENY;
        } else {
            return FilterReply.ACCEPT;
        }
    }
}