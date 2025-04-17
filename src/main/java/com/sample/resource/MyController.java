package com.sample.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test1")
@RestController
public class MyController {

    @GetMapping("/api/gogo")
    public String getResource() {
        return "This is a protected resource";
    }

}
