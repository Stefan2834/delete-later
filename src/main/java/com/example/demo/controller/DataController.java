package com.example.demo.controller;

import com.example.demo.mongo.MyEntity;
import com.example.demo.mongo.MyEntityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DataController {

    private final MyEntityInterface myEntityRepository;

    @Autowired
    public DataController(MyEntityInterface myEntityRepository) {
        this.myEntityRepository = myEntityRepository;
    }

    private static class Data {
        public String data;

        public String newData;

    }

    @GetMapping("/data")
    ResponseEntity<Response<?>> getJson() {
        List<MyEntity> result = (List<MyEntity>) myEntityRepository.findAll();
        Response<List<MyEntity>> response = new Response<>(true, result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/data")
    ResponseEntity<Response<?>> postJson(@RequestBody Data searchData) {
        List<MyEntity> result = (List<MyEntity>) myEntityRepository.findAll();

        boolean containsValue = result.stream()
                .anyMatch(entity -> entity.data.equals(searchData.data));


        if (!containsValue) {
            MyEntity newData = new MyEntity();
            newData.data = searchData.data;
            myEntityRepository.save(newData);
            Response<String> response = new Response<>(true, "Data added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            String message = "Data already in the collection";
            Response<String> response = new Response<>(false, message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/data")
    ResponseEntity<Response<?>> putJson(@RequestBody Data searchData) {
        MyEntity result = myEntityRepository.findByData(searchData.data);

        if (result != null && searchData.newData != null) {
            result.data = searchData.newData;
            myEntityRepository.save(result);
            Response<String> response = new Response<>(true, searchData.data + " changed successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Response<String> response = new Response<>(true, searchData.data + " cannot be changed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/data")
    ResponseEntity<Response<?>> deleteJson(@RequestBody Data searchData) {
        MyEntity result = myEntityRepository.findByData(searchData.data);
        if (result != null) {
            myEntityRepository.delete(result);
            Response<String> response = new Response<>(true, searchData.data + " deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Response<String> response = new Response<>(true, searchData.data + " cannot be deleted");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}

record Response<T>(boolean success, T data) {
}
