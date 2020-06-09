package com.example.helloservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.logging.Logger;

@RestController
public class HelloController {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    @Autowired
    private DiscoveryClient client;
    @Value("${server.port}")
    String port;

    @RequestMapping("/hello")
    public String index() throws Exception{
        ServiceInstance instance = client.getLocalServiceInstance();
        //让线程等待几秒钟
        int sleepTime = new Random().nextInt(3000);
        logger.info("sleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        logger.info("/hello, host:" + instance.getHost()+ "， service id:" +
                instance.getServiceId());
        return "Hello World from port = "+port;
    }
}
