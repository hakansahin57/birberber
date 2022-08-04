package com.birberber.services.imports.impl;

import com.birberber.domain.address.Country;
import com.birberber.services.imports.ExcelImportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component("excelImportService")
public class DefaultExcelImportService implements ExcelImportService {

    public List<Country> excelImport(MultipartFile file) {
        List<Country> countryList = new ArrayList<>();
        String sname = "";
        String scountryCode = "";


        long start = System.currentTimeMillis();

        FileInputStream inputStream;
        try {
            inputStream = (FileInputStream) file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            scountryCode = String.valueOf(nextCell.getNumericCellValue());
                            System.out.println(scountryCode);
                            break;
                        case 1:
                            sname = nextCell.getStringCellValue();
                            System.out.println(sname);
                            break;
                    }
                    countryList.add(new Country(scountryCode, sname));
                }
            }

            workbook.close();
            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return countryList;
    }

}
