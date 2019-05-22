package com.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by simon on 5/22/2019.
 */
public class ReadExcel {

    public List<String[]> readAllrow(String sheet1,int rowEnd, int numCol, File file0) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String[] record = null;
        List<String[]> rowFound = new ArrayList<String[]>();
        int i=0;
        try {
            file = new FileInputStream(file0);
            // Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(sheet1);
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            Cell cell = null;

            while (rowIterator.hasNext()) {

                Row row = (Row) rowIterator.next();

                int col = 0;
                cell = row.getCell(col);
                if (cell == null){
                    break;
                }
                record = new String[numCol];
                while (col < numCol) {
                    // Update the value of cell
                    cell = row.getCell(col);
                    if (cell!=null){
                        record[col] = cell.getStringCellValue();
                        col++;
                    }
                }
                Arrays.stream(record).forEach(a->{log.info(a.toString());});
                rowFound.add(record);
            }
        } finally {
            System.out.println("VI continuamos leyenfd0="+(++i));
            if (file != null) {
                file.close();
            }
        }
        System.out.println("exit-ccccccccccccccc--");
        return rowFound;
    }
}
