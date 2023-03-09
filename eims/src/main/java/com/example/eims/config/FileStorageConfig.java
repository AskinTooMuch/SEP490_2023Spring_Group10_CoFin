package com.example.eims.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;

@ConfigurationProperties(prefix="file")
@Component
@Data
public class FileStorageConfig {
    private String uploadDir;

}
