package com.rest;

import com.service.DataFillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.FileNotFoundException;

/**
 * Created by simon on 5/17/2019.
 */
@RestController
@RequestMapping("/datafill")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class CreateDataRest {
    @Inject
    private DataFillService dataFillService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAll() throws FileNotFoundException {
        dataFillService.createAll();


        return new ResponseEntity("All Data Created", HttpStatus.CREATED);
    }
}
