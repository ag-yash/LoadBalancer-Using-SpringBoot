package com.springboot.loadbalancer.backend_service.Controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MainController {
    @GetMapping("/hello")
    public String fun() throws Exception {
        URL url = new URL("http://checkip.amazonaws.com"); //gets the public IP of the instance
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String ip = br.readLine();
        return "Hello from Backend Service running at IP: " + ip + " at time: " + LocalDateTime.now();
    }

}
