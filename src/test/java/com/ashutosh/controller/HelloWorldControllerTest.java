package com.ashutosh.controller;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HelloWorldControllerTest {
    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    void helloWorldEndpointRespondsWithProperContent(){
        var response = client.toBlocking().retrieve("/hello");
        assertEquals("Hello from service", response);
    }

    @Test
    void helloWorldEndpointRespondsWithProperContentAndStatusCode(){
        var response = client.toBlocking().exchange("/hello", String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello from service", response.getBody().get());
    }

    @Test
    void helloConfigEndpointRespondsWithProperContentAndStatusCode(){
        var response = client.toBlocking().exchange("/hello/config", String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello from config", response.getBody().get());
    }

    @Test
    void helloTranslationConfigEndpointRespondsWithProperContentAndStatusCode(){
        var response = client.toBlocking().exchange("/hello/translation", String.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello World", response.getBody().get());
    }
}
