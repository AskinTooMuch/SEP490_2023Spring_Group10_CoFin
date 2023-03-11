/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 08/03/2023   1.0         ChucNV      First Deploy<br>
 * 11/03/2023   2.0         ChucNV      Add resourceToX64 method<br>
 */
package com.example.eims.service.impl;

import com.example.eims.config.FileStorageConfig;
import com.example.eims.service.CustomFileNotFoundException;
import com.example.eims.service.FileStorageException;
import com.example.eims.service.interfaces.IFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileStorageServiceImpl implements IFileStorageService {
    private final Path fileStorageLocation;

    /**
     * Constructor for file storage services
     * @param fileStorageConfig configuration of file storing
     */
    @Autowired
    public FileStorageServiceImpl(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir());
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("could not create the directory for upload");
        }
    }

    /**
     * Storing files
     * @param file input
     * @return fileName
     */
    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioException) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ioException);
        }
    }

    /**
     * Load file with file name as resource object
     * @param fileName file name
     * @return resource obj
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource =  new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new CustomFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException mue) {
            throw new CustomFileNotFoundException("File not found " + fileName);
        }
    }

    /**
     * Convert resource to x64
     * @param resource file resource
     * @return resource obj
     */
    public String resourceToX64(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            return base64String;
        } catch (IOException ioException) {
            throw new CustomFileNotFoundException("Convert failed");
        }
    }
}
