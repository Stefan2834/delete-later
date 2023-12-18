package com.example.demo.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "spring")
public class MyEntity {
    
    
    @Id
    public String _id;
    public String data;
    
    
}