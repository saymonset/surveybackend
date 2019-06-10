package com.service;

import com.enums.RolNombre;
import com.model.mongo.*;
import com.repository.mongo.*;
import com.tools.ToJson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;


/**
 * Created by simon on 5/17/2019.
 */
@Service
@Transactional
public class DataFillService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ResourceLoader resourceLoader;
    @Inject
    private ToJson toJson;
    @Inject
    private TreeModelTerritorialRepository treeModelMongoRepository;
    @Inject
    private EncuestaRepository encuestaRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private SendSurveyRepository mandoEncuestaRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Inject
    private TreeModelServicioRepository treeModelServicioRepository;
    @Inject
    private RolService rolService;
    @Autowired
    UsuarioService usuarioService;
    // CREATE DATA /////////////////////////////////////////////////////////////
    public void createAll(File file1 ) {
        usuarioRoot("root@gmail.com");
        cargarCompanyExcel(file1);

    }

    private void cargarCompanyExcel(File file1 ) {

     //   File file1 = null;
        try {
           /* file1 = ResourceUtils.getFile(
                    "classpath:data/datamonitorear/data.xlsx");*/
            //readAllrow("divisiónTerritorial", 8,file1);
            Company company =   readCompany("company", 3,file1);
            usuario(company.getEmail(), company);
            rol(  company);
            survey(company, file1);
            treeModelTerritorial(company, file1);
            treeModelSservicio(company, file1);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void survey(Company company, File file1) {

       // File file1 = null;
        try {
           /* file1 = ResourceUtils.getFile(
                    "classpath:data/datamonitorear/data.xlsx");*/
            //readAllrow("divisiónTerritorial", 8,file1);
            readEncuestas("Encuestas", 4,file1, company);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }





    private void treeModelTerritorial(Company company,  File file1) {

      // File file1 = null;
        try {
            /*file1 = ResourceUtils.getFile(
                    "classpath:data/datamonitorear/data.xlsx");*/
            //readAllrow("divisiónTerritorial", 8,file1);
            readAllrow("divisiónTerritorial", 8,file1,company);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void treeModelSservicio(Company company, File file1) {

       // File file1 = null;
        try {
        /*    file1 = ResourceUtils.getFile(
                    "classpath:data/datamonitorear/data.xlsx");*/
            //readAllrow("divisiónTerritorial", 8,file1);
            servicioReadAllrow("divisiónServicios", 8,file1, company);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void readAllrow(String sheet1, int numCol, File file0, Company company) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
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
            TreeModelTerritorial treeModel = null;
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
                        /**Buscamos el parent node de la hoj de excel*/
                        Cell parentNodeBefore = null;
                        try {
                            parentNodeBefore = row.getCell(node - 2);
                        }catch (Exception e){
                            parentNodeBefore = null;
                        }

                        if (null == parentNodeBefore){
                            parentNode = "-1";
                        }else{
                            parentNode = parentNodeBefore.getStringCellValue();
                        }
                        /** Fin Buscamos el parent node de la hoj de excel*/

                        /**Si no existe el parentNode.. lo creamos*/
                        String codigo = cell.getStringCellValue();;
                        TreeModelTerritorial treeModelc  = treeModelMongoRepository.findByNodeAndCompany(codigo, company);
                        if (treeModelc == null ){
                            treeModel = new TreeModelTerritorial();

                            value = row.getCell(node + 1).getStringCellValue();
                            treeModel.setCompany(company);
                            treeModel.setParentNode(parentNode);
                            treeModel.setChildren(null);
                            treeModel.setValue(value);
                            treeModel.setNode(codigo);
                            treeModel.setDivisionTerritorial(codigo);
                            treeModelMongoRepository.save(treeModel);
                        }


                        node += 2;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }


    public Company readCompany(String sheet1, int numCol, File file0) throws Exception {
        Company enc  = null;
                Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
        List<String[]> rowFound = new ArrayList<String[]>();
        Survey encuesta = null;
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
                        String code = row.getCell(node++).getStringCellValue();
                        String name = row.getCell(node++).getStringCellValue();
                        String email = row.getCell(node++).getStringCellValue();

                          enc  = companyRepository.
                                findByCode(code);
                        if (enc == null ){
                            enc = new Company ();
                            enc.setCode(code);
                            enc.setName(name);
                            enc.setEmail(email);
                            companyRepository.save(enc);
                        }


                        node += 3;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
        return enc;
    }


    public void rol(Company company) throws Exception {

        RolNombre roleAdmin = RolNombre.ROLE_ADMIN;
        RolNombre rolUser = RolNombre.ROLE_USER ;
        Rol rol = null;
                Optional<Rol> rolBd =  rolService.getByRolNombreAndCompany(roleAdmin, company);
        if (!rolBd.isPresent()){
            rol = new Rol(roleAdmin, company);
            rolService.save(rol);
        }
        rolBd =  rolService.getByRolNombreAndCompany(rolUser, company);
        if (!rolBd.isPresent()){
            rol = new Rol(rolUser, company);
            rolService.save(rol);
        }
    }


    public void readEncuestas(String sheet1, int numCol, File file0, Company company) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
        List<String[]> rowFound = new ArrayList<String[]>();
        Survey encuesta = null;
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
                        String codigoEncuesta = row.getCell(node++).getStringCellValue();
                        String divisionTerritorial = row.getCell(node++).getStringCellValue();
                        String divisionServicios = row.getCell( node ++).getStringCellValue();
                        String encuestaFile = row.getCell( node ++).getStringCellValue();
                      /*  if ("Coche-17".equalsIgnoreCase(codigoEncuesta)){
                            System.out.println("");
                        }*/

                        Survey enc  = encuestaRepository.
                                findByFileEncuestaAndDivisionTerritorialAndDivisionServiciosAndCompanyAndCodigoEncuesta(encuestaFile,divisionTerritorial,
                                        divisionServicios,company, codigoEncuesta);
                         if (enc == null ){

                             encuesta = new Survey();
                             encuesta.setCompany(company);
                             encuesta.setCodigoEncuesta(codigoEncuesta);
                             encuesta.setDivisionTerritorial(divisionTerritorial);
                             encuesta.setDivisionServicios( divisionServicios);
                             encuesta.setFileEncuesta(encuestaFile);
                             encuestaRepository.save(encuesta);
                         }


                        node += 1;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }


    public void usuarioRoot(String email){

        Optional<Usuario> usu = usuarioService.findByEmail(email);
        if (!usu.isPresent()){
            Usuario usuario =
                    new Usuario("root", "root",email,
                            passwordEncoder.encode("123456"));
            Set<Rol> roles = new HashSet<>();
            usuario.setRoles(roles);
            usuarioService.guardar(usuario);
        }

    }

    public void usuario(String email, Company company){

        Optional<Usuario> usu = usuarioService.findByEmail(email);
        if (!usu.isPresent()){
            Usuario usuario =
                    new Usuario("admin", "admin",email,
                            passwordEncoder.encode("123456"));

            Set<Rol> roles = new HashSet<>();
          /*  Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get();
            roles.add(rolAdmin);
            Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();*/
            roles.add(new Rol(RolNombre.ROLE_ADMIN, company));
            roles.add(new Rol(RolNombre.ROLE_USER, company));
            usuario.setRoles(roles);
            usuario.setCompany(company);
            usuarioService.guardar(usuario);
        }

    }


    public void sendEncuestas(String sheet1, int numCol, File file0) throws Exception {
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

    public void servicioReadAllrow(String sheet1, int numCol, File file0, Company company) throws Exception {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        FileInputStream file = null;
        String parentStr = null;
        String parentNode = null;
        String value = null;
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
            boolean titleRow = true;
            TreeModelServicio treeModel = null;
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
                        /**Buscamos el parent node de la hoj de excel*/
                        Cell parentNodeBefore = null;
                        try {
                            parentNodeBefore = row.getCell(node - 2);
                        }catch (Exception e){
                            parentNodeBefore = null;
                        }

                        if (null == parentNodeBefore){
                            parentNode = "-1";
                        }else{
                            parentNode = parentNodeBefore.getStringCellValue();
                        }
                        /** Fin Buscamos el parent node de la hoj de excel*/

                        /**Si no existe el parentNode.. lo creamos*/
                        String codigo = cell.getStringCellValue();;
                        TreeModelServicio treeModelc  = treeModelServicioRepository.findByNodeAndCompany(codigo, company);
                        if (treeModelc == null ){
                            treeModel = new TreeModelServicio();
                            treeModel.setCompany(company);
                            value = row.getCell(node + 1).getStringCellValue();
                            treeModel.setParentNode(parentNode);
                            treeModel.setChildren(null);
                            treeModel.setValue(value);
                            treeModel.setNode(codigo);
                            treeModel.setDivisionServicios(codigo);
                            treeModelServicioRepository.save(treeModel);
                        }


                        node += 2;
                    }
                }
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }

    public void createPositioning() {
        Resource resource = resourceLoader.getResource("classpath:data/json/csvjson.json");
        Map<String, Object> map = toJson.stringToMap(toJson.resourceToString(resource));
        ArrayList data = (ArrayList) map.get("DATA");
        TreeModelTerritorial treeModel = null;
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            treeModel = new TreeModelTerritorial();
            treeModel.setParentNode((String.valueOf(dist.get("parent"))));
            treeModel.setChildren(new ArrayList<>());
            treeModel.setValue(String.valueOf(dist.get("value")));
            treeModel.setNode((String.valueOf(dist.get("id"))));
            treeModelMongoRepository.save(treeModel);
        }
    }


}
