package com.rest;

import com.dozer.DozerHelper;
import com.dto.TreeModelDTO;
import com.model.TreeModelMongo;
import com.repository.TreeModelMongoRepository;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/positioning")
public class TreeModelMongoRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private TreeModelMongoRepository treeModelMongoRepository;
    @Inject
    private DozerBeanMapper dozerBeanMapper;
   /* @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(treeModelMongoRepository.findAll(), HttpStatus.CREATED);
    }*/


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TreeModelDTO> findAll()  {
        List<TreeModelMongo> tree = treeModelMongoRepository.findAll();
        List<TreeModelDTO> TreeModelDTOS = DozerHelper.map(dozerBeanMapper, tree, TreeModelDTO.class);
       return TreeModelDTOS;
       /* *
         * public List<ListUserDTO> getActiveUsers() {
         return DozerHelper.map(dozerBeanMapper, userRepository.findAll(), ListUserDTO.class);
         }
         * */


    }

}
