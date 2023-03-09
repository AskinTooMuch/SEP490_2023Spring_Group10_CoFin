package com.example.eims.dto.file;

import lombok.Data;

@Data
public class FileResponse {
    private String filename;
    private String fileDownloadUri;
    private String fileType;
    private Long fileSize;

    public FileResponse(String filename, String fileDownloadUri, String fileType, Long fileSize) {
        this.filename = filename;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
