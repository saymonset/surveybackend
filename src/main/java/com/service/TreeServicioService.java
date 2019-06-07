package com.service;
import com.model.mongo.Company;
import com.model.mongo.TreeModelServicio;
import com.repository.mongo.TreeModelServicioRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 5/18/2019.
 */
@Service
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

        List<TreeModelServicio> childs =  getChilds( childsRoot, company );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelServicio> getChilds(List<TreeModelServicio> trees, Company company ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                childParent.setChildren(getChilds( childs, company ));
            }else{
                childParent.setChildren(null);
            }
        });

        return trees;
    }

    /**Dame la marcas  de este nodo y recursivo .. */
    public List<TreeModelServicio> getMarcaChilds(String node, Company company) {
        List<TreeModelServicio> marcaChildsRoot = new ArrayList<>();
        TreeModelServicio tree = treeModelServicioRepository.findByNodeAndCompany(node, company);
        List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(node, company);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChilds( childs, marcaChildsRoot, company );
        }

        return marcaChildsRoot;
    }

    /**Dame la marcas  hijos*/
    private List<TreeModelServicio> getMarcaChilds(List<TreeModelServicio> trees, List<TreeModelServicio> marcaChildsRoot, Company company ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNodeAndCompany(childParent.getNode(), company);
            if (childs!=null && childs.size() > 0){
                getMarcaChilds( childs, marcaChildsRoot, company );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }

}
