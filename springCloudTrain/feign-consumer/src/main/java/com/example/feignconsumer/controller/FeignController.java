package com.example.feignconsumer.controller;

import com.example.feignconsumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    HelloService helloService;

    @RequestMapping("/")
    public String hello(){
        long start = System.currentTimeMillis();
        String result = helloService.hello();
        long end = System.currentTimeMillis();
        return result + " , Spend time" + (end-start);
    }
}

