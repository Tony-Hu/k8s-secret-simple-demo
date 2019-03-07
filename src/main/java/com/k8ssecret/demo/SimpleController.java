package com.k8ssecret.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    Environment env;

    @GetMapping("/")
    public String getProperties() {
        StringBuilder sb = new StringBuilder();
        String userName = System.getenv("MY_USERNAME");
        String password = System.getenv("MY_PASSWORD");
        if (userName == null) {
            userName = env.getProperty("my.username");
        }
        if (password == null) {
            password = env.getProperty("my.password");
        }
        sb.append("The name is: \"").append(userName).append("\"\n");
        sb.append("The password is: \"").append(password).append("\"\n");
        return sb.toString();
    }
}
