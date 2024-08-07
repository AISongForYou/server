package org.ktmiracle100.songforyou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://front.songforyou.p-e.kr", "http://localhost:3000")
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000);
    }
}