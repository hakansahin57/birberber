package com.birberber.services.imports;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ExcelImportService {
    <T> List<T> excelImport(MultipartFile file);
}
