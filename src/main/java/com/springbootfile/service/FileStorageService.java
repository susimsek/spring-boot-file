package com.springbootfile.service;

import com.springbootfile.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    public File save(MultipartFile multipartFile);
    public File findById(String fileId);
    public List<File> findAll();
    public void delete(String fileId);

}
