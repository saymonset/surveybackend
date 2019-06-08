package com.rest;

import com.payload.UploadFileResponse;
import com.service.DataFillService;
import com.tools.FileTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

         File file1 = null;
        try {
            file1 = ResourceUtils.getFile(
                    "classpath:data/datamonitorear/data.xlsx");
            dataFillService.createAll(file1);

        }catch (Exception e) {
            e.printStackTrace();
        }



        return new ResponseEntity("All Data Created", HttpStatus.CREATED);
    }

    @RequestMapping(path = "/all", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN')")
    public UploadFileResponse createAllPost(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        try {
            File file1 = FileTool.convert(file);
            dataFillService.createAll(file1);
            uploadFileResponse.setMessage("Sucessfully");
        } catch (IOException e) {
            e.printStackTrace();
        }




        return new UploadFileResponse();
    }
}
