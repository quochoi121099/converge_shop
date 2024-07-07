package com.studyprogramming.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.user-upload-dir}")
    private String userUploadDir;

    @Value("${file.product-upload-dir}")
    private String productUploadDir;

    @Value("${file.sale-upload-dir}")
    private String saleUploadDir;

    public List<String> storeFile(MultipartFile[] files, String uploadDir) throws IOException {
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            // Lấy phần mở rộng của tệp
            String fileExtension = getFileExtension(file.getOriginalFilename());

            // Tạo tên tệp mới với UUID
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;

            // Xác định vị trí lưu tệp
            Path copyLocation = Paths.get(uploadDir).resolve(fileName).normalize();

            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(copyLocation.getParent());

            // Lưu tệp
            Files.copy(file.getInputStream(), copyLocation);

            fileNames.add(fileName);
        }

        return fileNames;
    }

    public List<String> storeUserFiles(MultipartFile[] files) throws IOException {
        return storeFile(files, userUploadDir);
    }

    public List<String> storeProductFiles(MultipartFile[] files) throws IOException {
        return storeFile(files, productUploadDir);
    }

    public List<String> storeSaleFiles(MultipartFile[] files) throws IOException {
        return storeFile(files, saleUploadDir);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
