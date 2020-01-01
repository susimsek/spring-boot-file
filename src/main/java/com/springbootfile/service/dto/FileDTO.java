package com.springbootfile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
