package com.service;

import com.model.mongo.SendSurvey;
import com.repository.mongo.SendSurveyRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by simon on 5/24/2019.
 */
@Service
public class SendSurveyService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private SendSurveyRepository mandoEncuestaRepository;




    public void sendSurvey(String sheet1, int numCol, File file0) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
        List<String[]> rowFound = new ArrayList<String[]>();
        SendSurvey mandoEncuesta = null;
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
            boolean titleRow = true;
            while (rowIterator.hasNext()) {

                Row row = (Row) rowIterator.next();
                if (row == null){
                    break;
                }

                int node = 0;

                /**Si son los titulos , que es la [rimera fila.. descartar*/
                if (titleRow){
                    titleRow = false;
                    continue;
                }
                while (node < numCol) {
                    // Update the value of cell
                    cell = row.getCell(node);
                    if (cell == null){
                        break;
                    }

                    if (cell!=null){


                        /**Si no existe el parentNode.. lo creamos*/
                        String name = row.getCell(node).getStringCellValue();
                        String lastName = row.getCell( node + 1).getStringCellValue();
                        String email = row.getCell( node + 2).getStringCellValue();
                        String divisionTerritorial = row.getCell( node + 3).getStringCellValue();
                        String divisionServicios = row.getCell( node + 4).getStringCellValue();
                        SendSurvey enc  = null;//mandoEncuestaRepository.findByNode("-1");

                        if (enc == null ){

                            mandoEncuesta = new SendSurvey();
                            mandoEncuesta.setDivisionTerritorial(divisionTerritorial);
                            mandoEncuesta.setDivisionServicios( divisionServicios);
                            mandoEncuesta.setName(name);
                            mandoEncuesta.setLastName(lastName);
                            mandoEncuesta.setEmail(email);
                            mandoEncuesta.setCreatedAt(new Date());
                            mandoEncuesta.setAnswered(false);
                            mandoEncuesta.setEmailViewed(false);
                            mandoEncuestaRepository.save(mandoEncuesta);
                        }


                        node += 5;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }




}
