package com.config;
import com.dozer.LazyFieldMapper;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 5/14/2019.
 */
@Configuration
public class DozerConfiguration {

    @Bean(name = "dozerBeanMapper")
    public DozerBeanMapper dozerBeanMapper() {
        List<String> mappingFiles = Arrays.asList("./dozerMapping/configuration.xml");

        Map<String, CustomConverter> customConvertersWithId = new HashMap<>();

        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        dozerBean.setCustomConvertersWithId(customConvertersWithId);
        dozerBean.setCustomFieldMapper(new LazyFieldMapper());

        return dozerBean;
    }

}