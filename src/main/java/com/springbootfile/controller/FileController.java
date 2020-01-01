package com.springbootfile.controller;

import com.springbootfile.model.File;
import com.springbootfile.service.FileStorageService;
import com.springbootfile.service.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public FileDTO uploadFile(@RequestParam("file") MultipartFile file) {

        File dbFile=fileStorageService.save(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        FileDTO fileDTO=new FileDTO(dbFile.getFileName(), fileDownloadUri,
        file.getContentType(), file.getSize());

        return fileDTO;
    }

    @PostMapping("/uploadMultipleFiles")
    public List<FileDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Veritabanından dosyayı al
        File dbFile = fileStorageService.findById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }


    @DeleteMapping("/deletedFile/{fileId}")
    public ResponseEntity deleteFile(@PathVariable String fileId) {
        // Veritabanından dosyayı sil
        fileStorageService.delete(fileId);
        return ResponseEntity.status(204).build();
    }

}
