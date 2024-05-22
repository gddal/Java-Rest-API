package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {
       
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true); // Include the query string in the log
        filter.setIncludePayload(true); // Include the payload (body of the request)
        filter.setMaxPayloadLength(10000); // Maximum length of the payload to be logged
        filter.setIncludeHeaders(true); // Include the headers in the log
        filter.setAfterMessagePrefix("REQUEST DATA : "); // Custom prefix for the log message
        return filter;
    }
}
