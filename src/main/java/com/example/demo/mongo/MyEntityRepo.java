package com.example.demo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyEntityRepo extends MongoRepository<MyEntity, String> {
    
    
    MyEntity findByData(String searchData);
    
    
}