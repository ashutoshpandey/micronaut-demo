package com.ashutosh.service;

import jakarta.inject.Singleton;

@Singleton
public class HelloWorldService {
    public String helloFromService(){
        return "Hello from service";
    }
}
