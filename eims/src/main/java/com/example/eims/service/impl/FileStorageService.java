/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 08/03/2023   1.0         ChucNV      First Deploy<br>
 */
package com.example.eims.service.impl;

import com.example.eims.config.FileStorageConfig;
import com.example.eims.service.CustomFileNotFoundException;
import com.example.eims.service.FileStorageException;
import com.example.eims.service.interfaces.IFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements IFileStorage {
    private final Path fileStorageLocation;

    /**
     * Constructor for file storage services
     * @param fileStorageConfig configuration of file storing
     */
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
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


}
