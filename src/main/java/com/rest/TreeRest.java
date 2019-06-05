package com.rest;

import com.dozer.DozerHelper;
import com.dto.TreeModelDTO;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.security.SecurityConstants;
import com.service.TreeServicioService;
import com.service.TreeTerritorialService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/tree")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class TreeRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    private DozerBeanMapper dozerBeanMapper;
    @Inject
    private TreeTerritorialService treeService;
    @Inject
    private TreeServicioService treeServicioService;
   /* @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(treeModelMongoRepository.findAll(), HttpStatus.CREATED);
    }*/


    @GetMapping(value = "/territorial", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TreeModelDTO> territorialfindAll()  {
        List<TreeModelTerritorial> tree =    treeService.getChildsTree();;
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
       return TreeModelDTOS;
    }
    @GetMapping(value = "/servicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TreeModelDTO> serviciofindAll()  {
        List<TreeModelServicio> tree =    treeServicioService.getChildsTree();;
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
        return TreeModelDTOS;
    }
}
