package com.example.demo.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.stream.DoubleStream;

@Document(collection = "spring")
public class MyEntity {


    @Id
    public String id;
    public String data;

}