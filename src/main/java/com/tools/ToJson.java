package com.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
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
}
