package com.service;

import com.model.mongo.TreeModel;
import com.repository.mongo.TreeModelRepository;
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
public class TreeService {
    @Inject
    private TreeModelRepository treeModelMongoRepository;




    public List<TreeModel> getTree() {
        List<TreeModel> childsRoot = treeModelMongoRepository.findByParentNode(-1);

        List<TreeModel> childs =  getChilds( childsRoot );
        return childs;
    }
        public List<TreeModel> getChilds(List<TreeModel> trees ){
            trees.forEach(child->{
                List<TreeModel> childs = treeModelMongoRepository.findByParentNode(child.getNode());
                if (childs!=null && childs.size() > 0){
                    child.setChildren(getChilds( childs ));
                }else{
                    child.setChildren(null);
                }
            });

        return trees;
    }


    private Long loadPositionrecursivo(long root, ArrayList data){
        TreeModel treeModelChild = null;
        TreeModel treeModel = null;
        List<TreeModel> children = new ArrayList();
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            if (StringUtils.isNotBlank(String.valueOf(dist.get("parent")))){
                long parent = Long.valueOf(String.valueOf(dist.get("parent")));
                if (root == parent){
                    treeModelChild = new TreeModel();
                    treeModelChild.setParentNode(root);
                    treeModelChild.setChildren(new ArrayList<>());
                    treeModelChild.setValue(String.valueOf(dist.get("value")));
                    treeModelChild.setNode(Long.valueOf(String.valueOf(dist.get("id"))));
                    children.add(treeModelChild);
                }
            }
        }
        TreeModel treeModelParent = treeModelMongoRepository.findByNode(root);
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
    }
}
