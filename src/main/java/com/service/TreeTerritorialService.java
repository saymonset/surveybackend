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

        List<TreeModelTerritorial> childs =  getChildsAndNietos( childsRoot, company );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelTerritorial> getChildsAndNietos(List<TreeModelTerritorial> trees, Company company ){
            trees.forEach(childParent->{
                List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(childParent.getNode(), company);
                if (childs!=null && childs.size() > 0){
                    childParent.setChildren(getChildsAndNietos( childs, company ));
                }else{
                    childParent.setChildren(null);
                }
            });

        return trees;
    }


    /**Dame los hijo  de este nodo y recursivo .. */
    public List<TreeModelTerritorial> getMarcaChildsNietos(String node, Company company) {
        List<TreeModelTerritorial> marcaChildsRoot = new ArrayList<>();
        TreeModelTerritorial tree = treeModelTerritorialRepository.findByNodeAndCompany(node,company);
        List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(node, company);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChildsNietos( childs, marcaChildsRoot, company );
        }

        return marcaChildsRoot;
    }




    /**Dame los  hijos y netos*/
    private List<TreeModelTerritorial> getMarcaChildsNietos(List<TreeModelTerritorial> trees, List<TreeModelTerritorial> marcaChildsRoot, Company company ){
        trees.forEach(childParent->{
            List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChildsNietos( childs, marcaChildsRoot, company );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }

    /**Dame los hijos  de este nodo */
    public List<TreeModelTerritorial> onlyChilds(String parentNode, Company company) {
        List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(parentNode, company);
        return childs;
    }

    /**Dame los hijos  de este nodo */
    public List<String> onlyChildNodeStrind(String parentNode, Company company) {
        List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(parentNode, company);
        List <String> chilldsServiciosStr = new ArrayList<>();
        if (null!= childs && childs.size() > 0){
            childs.forEach((e)-> {
                chilldsServiciosStr.add(e.getNode());
            });
        }

        return chilldsServiciosStr;
    }

    /**Dame los hijos  de este nodo y sus nietos*/
    public List<String> onlyChildNietosNodeStrind(String parentNode, Company company) {
        TreeModelTerritorial parent = treeModelTerritorialRepository.findByNodeAndCompany(parentNode, company);
        List <String> chilldsServiciosStr = new ArrayList<>();
        List<TreeModelTerritorial> trees =new ArrayList();// getChildsAndNietos(childs,  company );
        trees.add(parent);
        chilldsServiciosStr = getMarcaChildsNietosStr(trees,  chilldsServiciosStr,   company );

        return chilldsServiciosStr;
    }



    /**Dame los  hijos y netos*/
    private List <String> getMarcaChildsNietosStr(List<TreeModelTerritorial> trees, List<String> chilldsServiciosStr, Company company ){
        trees.forEach(childParent->{
            List<TreeModelTerritorial> childs = treeModelTerritorialRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChildsNietosStr( childs, chilldsServiciosStr, company );
            }else{
                chilldsServiciosStr.add(childParent.getNode());
            }
        });

        return chilldsServiciosStr;
    }


}
