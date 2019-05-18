package com.rest;

import com.dozer.DozerHelper;
import com.dto.TreeModelDTO;
import com.model.mongo.TreeModel;
import com.repository.mongo.TreeModelRepository;
import com.service.TreeService;
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
@RequestMapping("/positioning")
public class TreeRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private TreeModelRepository treeModelMongoRepository;
    @Inject
    private DozerBeanMapper dozerBeanMapper;
    @Inject
    private TreeService treeService;
   /* @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(treeModelMongoRepository.findAll(), HttpStatus.CREATED);
    }*/


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TreeModelDTO> findAll()  {
        List<TreeModel> tree =    treeService.getTree();;
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
       return TreeModelDTOS;
       /* *
         * public List<ListUserDTO> getActiveUsers() {
         return DozerHelper.map(dozerBeanMapper, userRepository.findAll(), ListUserDTO.class);
         }
         * */


    }

}
