package com.example.cloudinary.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    private CloudinaryConfig config;

    public AppConfig(CloudinaryConfig cloudinaryConfig) {
        this.config = cloudinaryConfig;
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(Map.of(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()
        ));
    }
}
