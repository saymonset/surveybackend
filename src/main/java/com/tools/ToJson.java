package com.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 5/17/2019.
 */
@Service
public class ToJson {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ResourceLoader resourceLoader;
    // AUXILIAR FUNCTIONS //////////////////////////////////////////////////////
    public String resourceToString(Resource resource) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            final InputStream inputStream = resource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            bufferedReader.close();
        } catch (IOException e) {
        }
        return stringBuilder.toString();
    }

    public Map<String, Object> stringToMap(String json) {
        HashMap<String, Object> result = null;

        try {
            result = new ObjectMapper().readValue(json, HashMap.class
            );

        } catch (IOException exception) {
            logger.info( exception.toString());
        }
        return result;
    }


    /**
     * Metodo que se encarga de devolver el nombre de archivo de una encuanta, segun los parametros
     *
     * @param nameFile nombre del archivo
     * @param language el lenguaje en el cual va a venir el archivo
     * @return
     */
    public Resource getResource(String nameFile, String language){

        Resource resource;
        String path = Constant.JSON_DATA + nameFile  + ".json";
        String pathLanguage = Constant.JSON_DATA + nameFile + "_" + language + ".json";

        if(language!=null){
            boolean exist = existResource(pathLanguage);

            if(exist){
                resource = resourceLoader.getResource(pathLanguage);
            }else {
                resource = resourceLoader.getResource(path);
            }

        } else {
            resource = resourceLoader.getResource(path);
        }

        return resource;

    } // end method


    /**
     * Metodo que evalua si el archivo existe en el path
     * @param path ruta del resource que vamos a evaluar
     * @return
     */
    private boolean existResource(String path){

        boolean exist = resourceLoader.getResource(path).exists();
        return exist;

    } // end method existResource
}
