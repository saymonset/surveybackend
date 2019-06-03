package com.service;
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

    /**Dame los hijos de los parents*/
    public List<TreeModelServicio> getTree() {
        List<TreeModelServicio> childsRoot = treeModelServicioRepository.findByParentNode("-1");

        List<TreeModelServicio> childs =  getChilds( childsRoot );
        return childs;
    }



    /**Dame los hijos  y recursivo .. los nietos de los hijos*/
    public List<TreeModelServicio> getChilds(List<TreeModelServicio> trees ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNode(childParent.getNode());
            if (childs!=null && childs.size() > 0){
                childParent.setChildren(getChilds( childs ));
            }else{
                childParent.setChildren(null);
            }
        });

        return trees;
    }

    /**Dame la marcas  de este nodo y recursivo .. */
    public List<TreeModelServicio> getMarcaChilds(String node) {
        List<TreeModelServicio> marcaChildsRoot = new ArrayList<>();
        TreeModelServicio tree = treeModelServicioRepository.findByNode(node);
        List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNode(node);
        /**Si estan en el ultimo escanio.. son marcas*/
        if (null != tree && (childs == null || childs.size() ==0)){
            marcaChildsRoot.add(tree);
        }else{
            getMarcaChilds( childs, marcaChildsRoot );
        }

        return marcaChildsRoot;
    }

    /**Dame la marcas  hijos*/
    private List<TreeModelServicio> getMarcaChilds(List<TreeModelServicio> trees, List<TreeModelServicio> marcaChildsRoot ){
        trees.forEach(childParent->{
            List<TreeModelServicio> childs = treeModelServicioRepository.findByParentNode(childParent.getNode());
            if (childs!=null && childs.size() > 0){
                getMarcaChilds( childs, marcaChildsRoot );
            }else{
                marcaChildsRoot.add(childParent);
            }
        });

        return trees;
    }

}
