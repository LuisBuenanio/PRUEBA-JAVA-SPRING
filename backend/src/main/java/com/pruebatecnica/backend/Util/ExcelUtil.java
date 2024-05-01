package com.pruebatecnica.backend.Util;

import com.pruebatecnica.backend.Entity.User;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {
    public static List<User> leerUsuariosDesdeExcel(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Suponiendo que la hoja de Excel tiene los usuarios en la primera hoja

        Iterator<Row> rowIterator = sheet.iterator();
        List<User> users = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) continue;

            User user = new User();
            user.setFirstname(row.getCell(0).getStringCellValue());
            user.setLastname(row.getCell(1).getStringCellValue());
            user.setEmail(row.getCell(2).getStringCellValue());
            user.setUsername(row.getCell(3).getStringCellValue());
            user.setPassword(row.getCell(4).getStringCellValue());

            users.add(user);
        }

        workbook.close();
        return users;
    }
}
