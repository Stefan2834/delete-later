package com.example.demo.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyEntityService {
    
    private final MyEntityRepo myEntityRepository;
    
    
    @Autowired
    public MyEntityService(MyEntityRepo myEntityRepository) {
        this.myEntityRepository = myEntityRepository;
    }
    
    public MyEntity findByData(String searchData) {
        return myEntityRepository.findByData(searchData);
    }
    
    public void save(MyEntity newData) {
        myEntityRepository.save(newData);
    }
    
    
    public List<MyEntity> findAll() {
        return myEntityRepository.findAll();
    }
    
    public void delete(MyEntity result) {
        myEntityRepository.delete(result);
    }
}
