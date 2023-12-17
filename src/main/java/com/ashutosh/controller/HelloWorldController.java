package com.ashutosh.controller;

import com.ashutosh.config.HelloWorldTranslationConfig;
import com.ashutosh.service.HelloWorldService;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/hello")
public class HelloWorldController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    private final String helloFromConfig;
    private HelloWorldService helloWorldService;
    private final HelloWorldTranslationConfig translationConfig;

    public HelloWorldController(HelloWorldService helloWorldService, @Property(name = "hello.world.message") String helloFromConfig, HelloWorldTranslationConfig translationConfig){
        this.helloFromConfig = helloFromConfig;
        this.helloWorldService = helloWorldService;
        this.translationConfig = translationConfig;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return helloWorldService.helloFromService();
    }

    @Get(uri = "/config", produces = MediaType.TEXT_PLAIN)
    public String helloConfig() {
        return helloFromConfig;
    }

    @Get(uri = "/translation", produces = MediaType.TEXT_PLAIN)
    public String helloTranslation() {
        return translationConfig.getEn();
    }
}
