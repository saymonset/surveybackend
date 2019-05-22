package com.service;

import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.TreeModelRepository;
import com.repository.mongo.TreeModelServicioRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 5/18/2019.
 */
@Service
public class TreeTerritorialService {
    @Inject
    private TreeModelRepository treeModelMongoRepository;



    public List<TreeModelTerritorial> getTree() {
        List<TreeModelTerritorial> childsRoot = treeModelMongoRepository.findByParentNode("-1");

        List<TreeModelTerritorial> childs =  getChilds( childsRoot );
        return childs;
    }

    public List<TreeModelTerritorial> getChilds(List<TreeModelTerritorial> trees ){
            trees.forEach(child->{
                List<TreeModelTerritorial> childs = treeModelMongoRepository.findByParentNode(child.getNode());
                if (childs!=null && childs.size() > 0){
                    child.setChildren(getChilds( childs ));
                }else{
                    child.setChildren(null);
                }
            });

        return trees;
    }
/*

    private Long loadPositionrecursivo(String root, ArrayList data){
        TreeModelTerritorial treeModelChild = null;
        TreeModelTerritorial treeModel = null;
        List<TreeModelTerritorial> children = new ArrayList();
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            if (StringUtils.isNotBlank(String.valueOf(dist.get("parent")))){
                String parent = String.valueOf(dist.get("parent"));
                if (root == parent){
                    treeModelChild = new TreeModelTerritorial();
                    treeModelChild.setParentNode(root);
                    treeModelChild.setChildren(new ArrayList<>());
                    treeModelChild.setValue(String.valueOf(dist.get("value")));
                    treeModelChild.setNode(String.valueOf(dist.get("id")));
                    children.add(treeModelChild);
                }
            }
        }
        TreeModelTerritorial treeModelParent = treeModelMongoRepository.findByNode(root);
        if (treeModelParent == null){
            children.forEach(tree->{
                treeModelMongoRepository.save(tree);
            });
        }else{
            if (treeModelParent.getChildren() == null ){
                treeModelParent.setChildren(new ArrayList<>());
            }
            treeModelParent.getChildren().addAll(children);
            treeModelMongoRepository.save(treeModelParent);
        }
        //treeModelChild = treeModelMongoRepository.save(treeModelChild);

        children.forEach(treeChild->{
                    loadPositionrecursivo(treeChild.getNode(), data);
                }
        );

        return null;
    }*/
}
