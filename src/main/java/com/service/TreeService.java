package com.service;

import com.model.TreeModelMongo;
import com.repository.TreeModelMongoRepository;
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
    private TreeModelMongoRepository treeModelMongoRepository;




    public List<TreeModelMongo> getTree() {
        List<TreeModelMongo> childsRoot = treeModelMongoRepository.findByParent(-1);

        List<TreeModelMongo> childs =  getChilds( childsRoot );
        return childs;
    }
        public List<TreeModelMongo> getChilds( List<TreeModelMongo> trees ){
            trees.forEach(child->{
                List<TreeModelMongo> childs = treeModelMongoRepository.findByParent(child.getId());
                if (childs!=null && childs.size() > 0){
                    child.setChildren(getChilds( childs ));
                }else{
                    child.setChildren(null);
                }
            });

        return trees;
    }


    private Long loadPositionrecursivo(long root, ArrayList data){
        TreeModelMongo treeModelChild = null;
        TreeModelMongo treeModelMongo = null;
        List<TreeModelMongo> children = new ArrayList();
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            if (StringUtils.isNotBlank(String.valueOf(dist.get("parent")))){
                long parent = Long.valueOf(String.valueOf(dist.get("parent")));
                if (root == parent){
                    treeModelChild = new TreeModelMongo();
                    treeModelChild.setParent(root);
                    treeModelChild.setChildren(new ArrayList<>());
                    treeModelChild.setValue(String.valueOf(dist.get("value")));
                    treeModelChild.setId(Long.valueOf(String.valueOf(dist.get("id"))));
                    children.add(treeModelChild);
                }
            }
        }
        TreeModelMongo treeModelParent = treeModelMongoRepository.findById(root);
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
                    loadPositionrecursivo(treeChild.getId(), data);
                }
        );

        return null;
    }
}
