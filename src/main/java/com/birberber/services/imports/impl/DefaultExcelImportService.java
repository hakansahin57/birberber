package com.birberber.services.imports.impl;

import com.birberber.services.imports.ExcelImportService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component("excelImportService")
public class DefaultExcelImportService implements ExcelImportService {

    private static final Logger LOG = Logger.getLogger(DefaultExcelImportService.class);

    public <T> List<T> excelImport(MultipartFile file) {
        FileInputStream inputStream;
        try {
            inputStream = (FileInputStream) file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Class<?> clazz = findTableDomain(sheet);
            List<Object> objectList = setValuesToInstance(sheet, clazz);
            workbook.close();
            return (List<T>) objectList;

        } catch (IOException |
                 ClassNotFoundException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    private List<Object> setValuesToInstance(Sheet sheet, Class<?> clazz) throws ClassNotFoundException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        List<Object> objectList = new ArrayList<>();
        // row 0 - column 0 is always name of the table
        // row 1 is for setter names, ex : setName
        // thats why starts from row 2
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Object obj = createInstance(clazz);
            for (int y = 0; y < sheet.getRow(i).getPhysicalNumberOfCells(); y++) {
                try {
                    BeanUtils.setProperty(obj, sheet.getRow(1).getCell(y).getStringCellValue(), sheet.getRow(i).getCell(y));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            objectList.add(obj);
        }
        return objectList;
    }

    private Class<?> findTableDomain(Sheet sheet) throws ClassNotFoundException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        // row 0 - column 0 is always name of the table
        String tableName = sheet.getRow(0).getCell(0).getStringCellValue();
        //  finds class type, ex : Country.class
        return Class.forName(tableName);
    }

    private Object createInstance(Class<?> clazz) throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        //  creates object for class
        return clazz.getDeclaredConstructor().newInstance();
    }

}
