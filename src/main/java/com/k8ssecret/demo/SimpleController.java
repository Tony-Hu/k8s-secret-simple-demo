package com.k8ssecret.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Value("${my.username}")
    private String userName;

    @Value("${my.password}")
    private String password;

    @Value("${my.base64Encode}")
    private String base64Decode;

    @GetMapping("/")
    public String getProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("The name is: \"").append(userName).append("\"<br>");
        sb.append("The password is: \"").append(password).append("\"<br>");
        sb.append("The base 64 decoded string is: \"").append(base64Decode).append("\"<br>");

        return sb.toString();
    }
}
