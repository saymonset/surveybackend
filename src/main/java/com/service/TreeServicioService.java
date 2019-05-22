package com.service;
import com.model.mongo.TreeModelServicio;
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
public class TreeServicioService {
    @Inject
    private TreeModelServicioRepository treeModelServicioRepository;


    public List<TreeModelServicio> getTree() {
        List<TreeModelServicio> childsRoot = treeModelServicioRepository.findByParentNode("-1");

        List<TreeModelServicio> childs =  getChilds( childsRoot );
        return childs;
    }

    public List<TreeModelServicio> getChilds(List<TreeModelServicio> trees ){
        trees.forEach(child->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNode(child.getNode());
            if (childs!=null && childs.size() > 0){
                child.setChildren(getChilds( childs ));
            }else{
                child.setChildren(null);
            }
        });

        return trees;
    }


  /*  private Long loadPositionrecursivo(String root, ArrayList data){
        TreeModelServicio treeModelChild = null;
        TreeModelServicio treeModel = null;
        List<TreeModelServicio> children = new ArrayList();
        for (Object distObject : data) {
            Map<String, Object> dist = (Map<String, Object>) distObject;
            if (StringUtils.isNotBlank(String.valueOf(dist.get("parent")))){
                String parent = String.valueOf(dist.get("parent"));
                if (root == parent){
                    treeModelChild = new TreeModelServicio();
                    treeModelChild.setParentNode(root);
                    treeModelChild.setChildren(new ArrayList<>());
                    treeModelChild.setValue(String.valueOf(dist.get("value")));
                    treeModelChild.setNode(String.valueOf(dist.get("id")));
                    children.add(treeModelChild);
                }
            }
        }
        TreeModelServicio treeModelParent = treeModelServicioRepository.findByNode(root);
        if (treeModelParent == null){
            children.forEach(tree->{
                treeModelServicioRepository.save(tree);
            });
        }else{
            if (treeModelParent.getChildren() == null ){
                treeModelParent.setChildren(new ArrayList<>());
            }
            treeModelParent.getChildren().addAll(children);
            treeModelServicioRepository.save(treeModelParent);
        }
        //treeModelChild = treeModelServicioRepository.save(treeModelChild);

        children.forEach(treeChild->{
                    loadPositionrecursivo(treeChild.getNode(), data);
                }
        );

        return null;
    }*/
}
