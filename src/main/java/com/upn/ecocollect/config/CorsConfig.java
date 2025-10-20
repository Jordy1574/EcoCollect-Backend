package com.upn.ecocollect.config; 
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 
import org.springframework.web.servlet.config.annotation.CorsRegistry; 

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}