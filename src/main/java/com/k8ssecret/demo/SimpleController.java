package com.k8ssecret.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class SimpleController {

    @Autowired
    Environment env;

    @GetMapping("/")
    public String getProperties() {
        StringBuilder sb = new StringBuilder();

        String userName = System.getenv("MY_USERNAME");
        String password = System.getenv("MY_PASSWORD");
        String base64Decode = System.getenv("BASE64_STRING");

        userName = userName == null ? env.getProperty("my.username") : userName;
        password = password == null ? env.getProperty("my.password") : password;
        base64Decode = base64Decode == null ? new String(Base64.getDecoder().decode(env.getProperty("my.base64Encode"))) : base64Decode;

        sb.append("The name is: \"").append(userName).append("\"<br>");
        sb.append("The password is: \"").append(password).append("\"<br>");
        sb.append("The base 64 decoded string is: \"").append(base64Decode).append("\"<br>");

        return sb.toString();
    }
}
