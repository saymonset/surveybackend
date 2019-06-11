package com.service;
import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
 
import com.repository.mongo.TreeModelServicioRepository;
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
public class TreeServicioService {
    @Inject
    private TreeModelServicioRepository treeModelServicioRepository;

    /**Dame root*/
    public TreeModelServicio getTree(Company company) {
        List<TreeModelServicio> childsRoot = treeModelServicioRepository.findByParentNodeAndCompany("-1", company);
        TreeModelServicio root = childsRoot.get(0);

        return root;
    }

    /**Dame los hijos de los parents*/
    public List<TreeModelServicio> getChildsTree(Company company) {
        List<TreeModelServicio> childsRoot = treeModelServicioRepository.findByParentNodeAndCompany("-1", company);

        List<TreeModelServicio> childs =  getChildsAndNietos( childsRoot, company );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelServicio> getChildsAndNietos(List<TreeModelServicio> trees, Company company ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                childParent.setChildren(getChildsAndNietos( childs, company ));
            }else{
                childParent.setChildren(null);
            }
        });

        return trees;
    }


    /**Dame los hijo  de este nodo y recursivo .. */
    public List<TreeModelServicio> getMarcaChildsNietos(String node, Company company) {
        List<TreeModelServicio> marcaChildsRoot = new ArrayList<>();
        TreeModelServicio tree = treeModelServicioRepository.findByNodeAndCompany(node,company);
        List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(node, company);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChildsNietos( childs, marcaChildsRoot, company );
        }

        return marcaChildsRoot;
    }




    /**Dame los  hijos y netos*/
    private List<TreeModelServicio> getMarcaChildsNietos(List<TreeModelServicio> trees, List<TreeModelServicio> marcaChildsRoot, Company company ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChildsNietos( childs, marcaChildsRoot, company );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }

    /**Dame los hijos  de este nodo */
    public List<TreeModelServicio> onlyChilds(String parentNode, Company company) {
        List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(parentNode, company);
        return childs;
    }

    /**Dame los hijos  de este nodo */
    public List<String> onlyChildNodeStrind(String parentNode, Company company) {
        List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(parentNode, company);
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
        TreeModelServicio parent = treeModelServicioRepository.findByNodeAndCompany(parentNode, company);
        List <String> chilldsServiciosStr = new ArrayList<>();
        List<TreeModelServicio> trees =new ArrayList();// getChildsAndNietos(childs,  company );
        trees.add(parent);
        chilldsServiciosStr = getMarcaChildsNietosStr(trees,  chilldsServiciosStr,   company );

        return chilldsServiciosStr;
    }



    /**Dame los  hijos y netos*/
    private List <String> getMarcaChildsNietosStr(List<TreeModelServicio> trees, List<String> chilldsServiciosStr, Company company ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChildsNietosStr( childs, chilldsServiciosStr, company );
            }else{
                chilldsServiciosStr.add(childParent.getNode());
            }
        });

        return chilldsServiciosStr;
    }

}
