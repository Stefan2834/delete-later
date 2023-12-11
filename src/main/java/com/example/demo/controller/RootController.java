package com.example.demo.controller;

import com.example.demo.config.MyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {


//    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


    @GetMapping("/")
    MyResponse getJson() {
//        return context.getBean(MyResponse.class);
        return new MyResponse("salut", true);
    }


}
