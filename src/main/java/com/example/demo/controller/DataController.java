package com.example.demo.controller;

import com.example.demo.mongo.MyEntity;
import com.example.demo.mongo.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/data")
public class DataController {

    private final MyEntityService myEntityService;

    @Autowired
    public DataController(MyEntityService myEntityService) {
        this.myEntityService = myEntityService;
    }

    private static class Data {
        public String data;
        public String newData;
    }

    @GetMapping
    ResponseEntity<Response<?>> getJson() {
        List<MyEntity> result = myEntityService.findAll();
        Response<List<MyEntity>> response = new Response<>(true, result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    ResponseEntity<Response<?>> postJson(@RequestBody Data searchData) {
        MyEntity containsValue = myEntityService.findByData(searchData.data);


        if (containsValue == null) {
            MyEntity newData = new MyEntity();
            newData.data = searchData.data;
            myEntityService.save(newData);
            Response<String> response = new Response<>(true, "Data added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            String message = "Data already in the collection";
            Response<String> response = new Response<>(false, message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping
    ResponseEntity<Response<?>> putJson(@RequestBody Data searchData) {
        MyEntity result = myEntityService.findByData(searchData.data);

        if (result != null && searchData.newData != null) {
            result.data = searchData.newData;
            myEntityService.save(result);
            Response<String> response = new Response<>(true, searchData.data + " changed successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Response<String> response = new Response<>(true, searchData.data + " cannot be changed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping
    ResponseEntity<Response<?>> deleteJson(@RequestBody Data searchData) {
        MyEntity result = myEntityService.findByData(searchData.data);
        if (result != null) {
            myEntityService.delete(result);
            Response<String> response = new Response<>(true, searchData.data + " deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Response<String> response = new Response<>(true, searchData.data + " cannot be deleted");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    record Response<T>(boolean success, T data) {
    }
}

