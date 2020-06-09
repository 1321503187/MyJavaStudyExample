package com.example.feignconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 通过FeignClient配置负载均衡，并指定服务提供者的名字
 */
@FeignClient(value = "hello-service", fallback = HelloServiceHystrix.class)
public interface HelloService {
    //这里指定调用的rest URL
    @GetMapping("/hello")
    String hello();
}
