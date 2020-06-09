package com.example.feignconsumer.service;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceHystrix implements HelloService {
    @Override
    public String hello() {
        return "Hello fall back";
    }
}
