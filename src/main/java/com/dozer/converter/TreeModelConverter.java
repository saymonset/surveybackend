package com.dozer.converter;

import com.dto.TreeModelDTO;
import com.model.mongo.TreeModelTerritorial;
import org.dozer.DozerConverter;


/**
 * Created by simon on 5/18/2019.
 */
public class TreeModelConverter extends DozerConverter<TreeModelTerritorial, TreeModelDTO> {

    public TreeModelConverter(Class<TreeModelTerritorial> prototypeA, Class<TreeModelDTO> prototypeB) {
        super(prototypeA, prototypeB);
    }

    @Override
    public TreeModelDTO convertTo(TreeModelTerritorial treeModel, TreeModelDTO treeModelDTO) {
        return null;
    }

    @Override
    public TreeModelTerritorial convertFrom(TreeModelDTO treeModelDTO, TreeModelTerritorial treeModel) {
        return null;
    }
}
