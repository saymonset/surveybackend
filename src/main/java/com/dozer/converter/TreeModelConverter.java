package com.dozer.converter;

import com.dto.TreeModelDTO;
import com.model.mongo.TreeModel;
import org.dozer.DozerConverter;


/**
 * Created by simon on 5/18/2019.
 */
public class TreeModelConverter extends DozerConverter<TreeModel, TreeModelDTO> {

    public TreeModelConverter(Class<TreeModel> prototypeA, Class<TreeModelDTO> prototypeB) {
        super(prototypeA, prototypeB);
    }

    @Override
    public TreeModelDTO convertTo(TreeModel treeModel, TreeModelDTO treeModelDTO) {
        return null;
    }

    @Override
    public TreeModel convertFrom(TreeModelDTO treeModelDTO, TreeModel treeModel) {
        return null;
    }
}
