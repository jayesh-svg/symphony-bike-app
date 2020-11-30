package com.symphony.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String uploadSites(MultipartFile file,Long userId) throws IOException, InvalidFormatException;
}
