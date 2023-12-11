package com.example.demo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyEntityInterface extends MongoRepository<MyEntity, String> {
    MyEntity findByData(String searchData);


}