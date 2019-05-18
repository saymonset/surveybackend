package com.service;

import com.model.TreeModelMongo;
import com.repository.TreeModelMongoRepository;
import com.tools.ToJson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by simon on 5/17/2019.
 */
@Service
public class DataFillService {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ResourceLoader resourceLoader;
    @Inject
    private ToJson toJson;
    @Inject
    private TreeModelMongoRepository treeModelMongoRepository;
    // CREATE DATA /////////////////////////////////////////////////////////////
    public void createAll() {
        createPositioning();
    }

    public void createPositioning() {
        Resource resource = resourceLoader.getResource("classpath:data/json/csvjson.json");
        Map<String, Object> map = toJson.stringToMap(toJson.resourceToString(resource));

        ArrayList data = (ArrayList) map.get("DATA");
        loadPositionrecursivo(-1,data);

    }

    private Long loadPositionrecursivo(long root, ArrayList data){
       TreeModelMongo treeModelChild = null;
        TreeModelMongo treeModelMongo = null;
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            if (StringUtils.isNotBlank(String.valueOf(dist.get("parent")))){
                long parent = Long.valueOf(String.valueOf(dist.get("parent")));
                if (root == parent){
                    treeModelChild = new TreeModelMongo();
                    treeModelChild.setParent(root);
                    treeModelChild.setValue(String.valueOf(dist.get("value")));
                    treeModelChild.setId(Long.valueOf(String.valueOf(dist.get("id"))));
                    treeModelChild = treeModelMongoRepository.save(treeModelChild);
                    TreeModelMongo treeModelParent = treeModelMongoRepository.findById(parent);

                    if (null == treeModelParent){
                        loadPositionrecursivo(treeModelChild.getId(), data);
                    } else{
                        if (treeModelParent.getChildren() == null ){
                            treeModelParent.setChildren(new ArrayList<>());
                        }
                        treeModelParent.getChildren().add(treeModelChild);
                        loadPositionrecursivo(treeModelChild.getId(), data);
                    }

                }

            }
        }
        return null;
    }
}
