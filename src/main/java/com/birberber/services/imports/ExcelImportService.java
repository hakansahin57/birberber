package com.birberber.services.imports;

import com.birberber.domain.address.Country;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelImportService {
    List<Country> excelImport(MultipartFile file);
}
