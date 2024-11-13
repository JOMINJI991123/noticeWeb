package com.example.noticeweb.api;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstApiController {

    public String hello(){

        return "hello";
    }

}
