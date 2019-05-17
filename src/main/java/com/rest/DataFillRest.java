package com.rest;

import com.service.DataFillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/datafill")
public class DataFillRest {
    @Inject
    private DataFillService dataFillService;
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> createAll() {
        dataFillService.createAll();
        return new ResponseEntity("All Data Created", HttpStatus.CREATED);
    }
}
