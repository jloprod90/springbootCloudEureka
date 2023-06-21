package com.formacionbanca.springbootserviceitem.RestTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean(name = "RestClient")
    @LoadBalanced
    public RestTemplate registryRestTemplate() {
        return new RestTemplate();
    }





}
