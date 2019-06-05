package com.service;

import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.TreeModelTerritorialRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 5/18/2019.
 */
@Service
public class TreeTerritorialService {
    @Inject
    private TreeModelTerritorialRepository treeModelTerritorialRepository;


    /**Dame root*/
    public TreeModelTerritorial getTree() {
        List<TreeModelTerritorial> childsRoot = treeModelTerritorialRepository.findByParentNode("-1");
        TreeModelTerritorial root = childsRoot.get(0);

        return root;
    }

    /**Dame los hijos de los parents*/
    public List<TreeModelTerritorial> getChildsTree() {
        List<TreeModelTerritorial> childsRoot = treeModelTerritorialRepository.findByParentNode("-1");

        List<TreeModelTerritorial> childs =  getChilds( childsRoot );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelTerritorial> getChilds(List<TreeModelTerritorial> trees ){
            trees.forEach(childParent->{
                List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNode(childParent.getNode());
                if (childs!=null && childs.size() > 0){
                    childParent.setChildren(getChilds( childs ));
                }else{
                    childParent.setChildren(null);
                }
            });

        return trees;
    }


    /**Dame la marcas  de este nodo y recursivo .. */
    public List<TreeModelTerritorial> getMarcaChilds(String node) {
        List<TreeModelTerritorial> marcaChildsRoot = new ArrayList<>();
        TreeModelTerritorial tree = treeModelTerritorialRepository.findByNodeAndCompany(node,null);
        List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNode(node);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChilds( childs, marcaChildsRoot );
        }

        return marcaChildsRoot;
    }

    /**Dame la marcas  hijos*/
    private List<TreeModelTerritorial> getMarcaChilds(List<TreeModelTerritorial> trees, List<TreeModelTerritorial> marcaChildsRoot ){
        trees.forEach(childParent->{
            List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNode(childParent.getNode());
            if (childs!=null && childs.size() > 0){
                getMarcaChilds( childs, marcaChildsRoot );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }
}
