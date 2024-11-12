package com.pdasilem.resourceservice.config;

import feign.Logger;
import org.apache.tika.Tika;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    Mp3Parser getMp3Parser() {
        return new Mp3Parser();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Tika getTika() {
        return new Tika();
    }
}
