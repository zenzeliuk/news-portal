package com.mainacademy.newsportal.api.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NewsapiClientConfig {

    @Bean
    public RestTemplate newsapiRestTemplate() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30_000);
        factory.setReadTimeout(30_000);
        return new RestTemplate(factory);
    }

}
