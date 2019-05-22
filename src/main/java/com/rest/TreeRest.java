package com.rest;

import com.dozer.DozerHelper;
import com.dto.TreeModelDTO;
import com.model.mongo.TreeModelServicio;
import com.model.mongo.TreeModelTerritorial;
import com.repository.mongo.TreeModelRepository;
import com.service.TreeServicioService;
import com.service.TreeTerritorialService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/tree")
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
        List<TreeModelTerritorial> tree =    treeService.getTree();;
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
       return TreeModelDTOS;
    }
    @GetMapping(value = "/servicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TreeModelDTO> serviciofindAll()  {
        List<TreeModelServicio> tree =    treeServicioService.getTree();;
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
        return TreeModelDTOS;
    }
}
