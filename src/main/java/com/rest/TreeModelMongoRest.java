package com.rest;

import com.repository.TreeModelMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/positioning")
public class TreeModelMongoRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    private TreeModelMongoRepository treeModelMongoRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(treeModelMongoRepository.findAll(), HttpStatus.CREATED);
    }
}
