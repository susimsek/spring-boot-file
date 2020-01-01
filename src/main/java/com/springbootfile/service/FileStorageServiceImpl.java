package com.springbootfile.service;

import com.springbootfile.exception.FileStorageException;
import com.springbootfile.model.File;
import com.springbootfile.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;

    @Override
    public File save(MultipartFile multipartFile) {
        // Dosya adını aldık.
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            // Dosya adının geçersiz karakterler içerip içermediğini kontrol edin
            if(fileName.contains("..")) {
                throw new FileStorageException("Afedersiniz! Dosya adı geçersiz path içeriyor " + fileName);
            }

            File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes());

            return fileRepository.save(file);
        } catch (IOException ex) {
            throw new FileStorageException("Dosya Yükleme başarısız. " + fileName + ". Lütfen tekrar deneyin!", ex);
        }

    }

    @Override
    public File findById(String fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public void delete(String fileId) {
        fileRepository.deleteById(fileId);
    }
}
