package com.jumongweb.GithubStats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public ModelConfig modelMapper() {
        return new ModelConfig();
    }
}
