package com.k8ssecret.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
public class SimpleController {

    @Autowired
    Environment env;

    @GetMapping("/")
    public String getProperties() {
        Map<String, String> environmentVariables = System.getenv();

        String userName = environmentVariables.getOrDefault("MY_USERNAME", env.getProperty("my.username"));
        String password = environmentVariables.getOrDefault("MY_PASSWORD", env.getProperty("my.password"));
        String base64Decode = environmentVariables.getOrDefault("BASE64_STRING", new String(Base64.getDecoder().decode(env.getProperty("my.base64Encode"))));

        StringBuilder sb = new StringBuilder();
        sb.append("The name is: \"").append(userName).append("\"<br>");
        sb.append("The password is: \"").append(password).append("\"<br>");
        sb.append("The base 64 decoded string is: \"").append(base64Decode).append("\"<br>");

        return sb.toString();
    }
}
