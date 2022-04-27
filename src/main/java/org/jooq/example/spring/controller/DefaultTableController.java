package org.jooq.example.spring.controller;

import org.jooq.example.spring.dto.EntitySave;
import org.jooq.example.spring.dto.InfoTable;
import org.jooq.example.spring.service.DefaultDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DefaultTableController {

    @Autowired
    private DefaultDBService defaultDBService;

    @PostMapping(value = "/find")
    public ResponseEntity find(@RequestBody InfoTable infoTable){
        List<Map> data;
        try {
            data = defaultDBService.select(infoTable);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody EntitySave entitySave){
        Map data;
        try {
            data = defaultDBService.save(entitySave);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(data, HttpStatus.OK);

    }


    @GetMapping(value = "/tablesName")
    public ResponseEntity getTablesName(){
        List<String> data;
        try {
            data = defaultDBService.showTablesDB();
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, HttpStatus.OK);
    }
}
