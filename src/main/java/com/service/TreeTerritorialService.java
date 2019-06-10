package com.service;

import com.model.mongo.Company;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.TreeModelTerritorialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 5/18/2019.
 */
@Service
@Transactional
public class TreeTerritorialService {
    @Inject
    private TreeModelTerritorialRepository treeModelTerritorialRepository;


    /**Dame root*/
    public TreeModelTerritorial getTree(Company company) {
        List<TreeModelTerritorial> childsRoot = treeModelTerritorialRepository.findByParentNodeAndCompany("-1", company);
        TreeModelTerritorial root = childsRoot.get(0);

        return root;
    }

    /**Dame los hijos de los parents*/
    public List<TreeModelTerritorial> getChildsTree(Company company) {
        List<TreeModelTerritorial> childsRoot = treeModelTerritorialRepository.findByParentNodeAndCompany("-1", company);

        List<TreeModelTerritorial> childs =  getChilds( childsRoot, company );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelTerritorial> getChilds(List<TreeModelTerritorial> trees, Company company ){
            trees.forEach(childParent->{
                List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(childParent.getNode(), company);
                if (childs!=null && childs.size() > 0){
                    childParent.setChildren(getChilds( childs, company ));
                }else{
                    childParent.setChildren(null);
                }
            });

        return trees;
    }


    /**Dame la marcas  de este nodo y recursivo .. */
    public List<TreeModelTerritorial> getMarcaChilds(String node, Company company) {
        List<TreeModelTerritorial> marcaChildsRoot = new ArrayList<>();
        TreeModelTerritorial tree = treeModelTerritorialRepository.findByNodeAndCompany(node,company);
        List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(node, company);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChilds( childs, marcaChildsRoot, company );
        }

        return marcaChildsRoot;
    }




    /**Dame la marcas  hijos*/
    private List<TreeModelTerritorial> getMarcaChilds(List<TreeModelTerritorial> trees, List<TreeModelTerritorial> marcaChildsRoot, Company company ){
        trees.forEach(childParent->{
            List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChilds( childs, marcaChildsRoot, company );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }
}
